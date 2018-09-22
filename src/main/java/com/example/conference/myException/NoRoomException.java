package com.example.conference.myException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRoomException extends Exception{

    public NoRoomException(String errorName) {
        super(errorName);
    }
}
