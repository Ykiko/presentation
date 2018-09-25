package com.example.conference.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNameProblemException extends Exception{

    public UserNameProblemException(String errorName) {
        super(errorName);
    }
}
