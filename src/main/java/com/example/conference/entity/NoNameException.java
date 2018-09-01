package com.example.conference.entity;

import org.springframework.beans.factory.annotation.Value;

public class NoNameException extends Exception{

    private String error1;

    public NoNameException(String errorName) {
        error1 = errorName;
    }

   /* public NoNamePresentationException() {

    }*/

    @Override
    public String toString() {
        return "NoNamePresentationException{" +
                "error1='" + error1 + '\'' +
                '}';
    }
}
