package com.example.conference.MyException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoNameUserException extends Exception{

    public NoNameUserException(String errorName) {
        super("NoNameUserException{" +
                "Error:'" + errorName + '\'' +
                '}');
    }
}
