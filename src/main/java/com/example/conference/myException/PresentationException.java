package com.example.conference.myException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PresentationException extends Exception{

    public PresentationException(String errorName) {
        super(errorName);
    }
}
