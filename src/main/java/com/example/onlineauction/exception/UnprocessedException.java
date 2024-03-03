package com.example.onlineauction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessedException extends RuntimeException {

    public UnprocessedException(String message) {
        super(message);
    }
}
