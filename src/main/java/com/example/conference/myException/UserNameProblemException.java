package com.example.conference.myException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNameProblemException extends Exception{

    public UserNameProblemException(String errorName) {
        super(errorName);
    }
}
