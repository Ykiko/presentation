package com.example.conference.MyException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends Exception{

    public NotFoundException(String errorName) {
        super("NotFoundException{" +
                "Error:'" + errorName + '\'' +
                '}');
    }
}
