package com.example.conference.MyException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CoincidesTimeException extends Exception{

    public CoincidesTimeException(String errorName) {
        super("CoincidesTimeException{" +
                "Error:'" + errorName + '\'' +
                '}');
    }
}
