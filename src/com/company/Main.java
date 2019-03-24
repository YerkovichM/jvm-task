package com.company;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.nio.Buffer;
import java.util.Scanner;

import static java.lang.Class.forName;

public class Main {

    //    private final static String controllerUpdatableClassPath = "com.company.ControllerUpdatable";
    private final static String textServiceUpdatableClassPath = "com.company.TextServiceUpdatable";
    private final static String classPath = "C:\\Users\\yerko\\IdeaProjects\\jvm-task\\out\\production\\jvm-task";

    public static void main(String[] args) throws Exception {
        taskClassLoader();
    }



    public static void taskClassLoader() throws Exception {
        while (true) {
            DynamicClassLoader loader = new DynamicClassLoader(new String[]{classPath});
            Class aClass = Class.forName(textServiceUpdatableClassPath, true, loader);
            IStaticTextMethod staticTextMethod = (IStaticTextMethod) aClass.newInstance();
            System.out.println(staticTextMethod.staticText());
            System.in.read();
        }
    }

//    public static void checkForClassLoader() throws Exception {
//        while (true) {
//            DynamicClassLoader loader = new DynamicClassLoader(new String[]{classPath});
//            Class aClass = forName(controllerUpdatableClassPath, true, loader);
//            System.out.println(aClass.newInstance());
//            System.in.read();
//        }
//    }
}
