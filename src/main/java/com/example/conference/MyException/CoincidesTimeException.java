package com.example.conference.MyException;

public class CoincidesTimeException extends Exception{

    private String error1;

    public CoincidesTimeException(String errorName) {
        super(errorName);
        error1 = errorName;
    }

    @Override
    public String toString() {
        return "CoincidesTimeException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
