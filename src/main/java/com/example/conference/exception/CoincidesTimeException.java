package com.example.conference.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CoincidesTimeException extends Exception{

    public CoincidesTimeException(String errorName) {
        super(errorName);
    }
}