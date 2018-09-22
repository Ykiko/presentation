package com.example.conference.myException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends Exception{

    public NotFoundException(String errorName) {
        super(errorName);
    }
}
