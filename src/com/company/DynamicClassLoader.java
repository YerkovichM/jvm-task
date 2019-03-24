package com.company;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DynamicClassLoader extends ClassLoader {
    private Map<String,Class> classesHash = new HashMap<>();
    private final String[] classPath;

    public DynamicClassLoader(String[] classPath) {
        this.classPath = classPath;
    }

    @Override
    protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result = findClass(name);
        if (resolve)
            resolveClass(result);
        return result;
    }

    @Override
    protected Class findClass(String name)
            throws ClassNotFoundException {
        Class result = (Class) classesHash.get(name);

        if (Objects.nonNull(result)) {
            return result;
        }
        if (!name.toLowerCase().contains("updatable")) {
            return findSystemClass(name);
        }

        File f = findFile(name.replace('.', '/'), ".class");
        if (Objects.isNull(f)) {
            return findSystemClass(name);
        }

        try {
            byte[] classBytes = loadFileAsBytes(f);
            result = defineClass(name, classBytes, 0,
                    classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(
                    "Cannot load class " + name + ": " + e);
        } catch (ClassFormatError e) {
            throw new ClassNotFoundException(
                    "Format of class file incorrect for class "
                            + name + " : " + e);
        }
        classesHash.put(name, result);
        return result;
    }

    @Override
    protected URL findResource(String name) {
        File f = findFile(name, "");
        if (Objects.isNull(f))
            return null;
        try {
            return f.toURL();
        } catch (java.net.MalformedURLException e) {
            return null;
        }
    }

    private File findFile(String name, String extension) {
        File f;
        for (int k = 0; k < classPath.length; k++) {
            f = new File((new File(classPath[k])).getPath()
                    + File.separatorChar
                    + name.replace('/',
                    File.separatorChar)
                    + extension);
            if (f.exists())
                return f;
        }
        return null;
    }

    private static byte[] loadFileAsBytes(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];
        FileInputStream f = new FileInputStream(file);
        try {
            f.read(result, 0, result.length);
        } finally {
            try {
                f.close();
            } catch (Exception e){}
        }
        return result;
    }
}