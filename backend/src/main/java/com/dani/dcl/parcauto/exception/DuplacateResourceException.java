package com.dani.dcl.parcauto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplacateResourceException extends RuntimeException{

    public DuplacateResourceException(String message) {
        super(message);
    }
}
