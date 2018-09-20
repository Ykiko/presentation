package com.example.conference.MyException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PresentationAlreadyExistsException extends Exception{

    public PresentationAlreadyExistsException(String errorName) {
        super("PresentationAlreadyExistsException{" +
                "Error:'" + errorName + '\'' +
                '}');
    }
}