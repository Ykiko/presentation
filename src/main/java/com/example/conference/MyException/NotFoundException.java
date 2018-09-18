package com.example.conference.MyException;

public class NotFoundException extends Exception{

    private String error1;

    public NotFoundException(String errorName) {
        super(errorName);
        error1 = errorName;
    }

    @Override
    public String toString() {
        return "NotFoundException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
