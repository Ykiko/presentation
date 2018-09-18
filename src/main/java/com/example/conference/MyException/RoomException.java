package com.example.conference.MyException;

public class RoomException extends Exception{

    private String error1;

    public RoomException(String errorName) {
        super(errorName);
        error1 = errorName;
    }

    @Override
    public String toString() {
        return "RoomException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
