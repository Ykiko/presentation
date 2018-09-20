package com.example.conference.MyException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRoomException extends Exception{

    public NoRoomException(String errorName) {
        super("NoRoomException{" +
                "Error:'" + errorName + '\'' +
                '}');
    }
}
