package com.company;

public class TextServiceUpdatable implements IStaticTextMethod {

    @Override
    public String staticText() {
        return "Some static text";
    }

    public String variable(String variable) {
        return variable;
    }

    public String exception(String text) throws RuntimeException {
        throw new RuntimeException();
    }
}
