package com.example.conference.myException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoomException extends Exception{

    public RoomException(String errorName) {
        super(errorName);
    }

}
