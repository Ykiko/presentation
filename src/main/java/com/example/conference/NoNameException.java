package com.example.conference;

public class NoNameException extends Exception{

    private String error1;

    public NoNameException(String errorName) {
        super(errorName);
        error1 = errorName;
    }

    @Override
    public String toString() {
        return "NoNamePresentationException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
