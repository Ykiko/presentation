package com.example.conference.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ListenerAlreadyRecordedException extends Exception {

    public ListenerAlreadyRecordedException(String errorName) {super(errorName);}
}
