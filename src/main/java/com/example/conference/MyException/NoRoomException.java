package com.example.conference.MyException;

public class NoRoomException extends Exception{

    private String error1;

    public NoRoomException(String errorName) {
        super(errorName);
        error1 = errorName;
    }

    @Override
    public String toString() {
        return "NoRoomException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
