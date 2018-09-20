package com.example.conference.MyException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoomException extends Exception{

    public RoomException(String errorName) {
        super("RoomException{" +
                "Error:'" + errorName + '\'' +
                '}');
    }

}
