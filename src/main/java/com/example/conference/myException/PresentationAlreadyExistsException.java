package com.example.conference.myException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PresentationAlreadyExistsException extends Exception{

    public PresentationAlreadyExistsException(String errorName) {
        super(errorName);
    }
}