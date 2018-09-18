package com.example.conference.MyException;

public class NoNameUserException extends Exception{

    private String error1;

    public NoNameUserException(String errorName) {
        super(errorName);
        error1 = errorName;
    }

    @Override
    public String toString() {
        return "NoNameUserException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
