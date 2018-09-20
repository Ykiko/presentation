package com.example.conference.MyException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PresentationException extends Exception{

    public PresentationException(String errorName) {
        super("PresentationException{" +
                "Error:'" + errorName + '\'' +
                '}');
    }
}
