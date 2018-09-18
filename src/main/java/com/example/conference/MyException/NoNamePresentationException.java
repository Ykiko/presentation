package com.example.conference.MyException;

public class NoNamePresentationException extends Exception{

    private String error1;

    public NoNamePresentationException(String errorName) {
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