package com.example.conference.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends Exception{

    public NotFoundException(String errorName) {
        super(errorName);
    }
}
