package com.example.conference.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRoomException extends Exception{

    public NoRoomException(String errorName) {
        super(errorName);
    }
}
