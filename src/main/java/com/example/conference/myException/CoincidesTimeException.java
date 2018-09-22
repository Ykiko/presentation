package com.example.conference.myException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CoincidesTimeException extends Exception{

    public CoincidesTimeException(String errorName) {
        super(errorName);
    }
}