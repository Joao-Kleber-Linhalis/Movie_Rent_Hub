package com.jk.BackEndLocadora.exceptions;

public class MaxDependentesAtivosExceededException extends RuntimeException {
    public MaxDependentesAtivosExceededException(String message) {
        super(message);
    }
}