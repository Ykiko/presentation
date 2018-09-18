package com.example.conference.MyException;

public class PresentationException extends Exception{

    private String error1;

    public PresentationException(String errorName) {
        super(errorName);
        error1 = errorName;
    }

    @Override
    public String toString() {
        return "PresentationException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
