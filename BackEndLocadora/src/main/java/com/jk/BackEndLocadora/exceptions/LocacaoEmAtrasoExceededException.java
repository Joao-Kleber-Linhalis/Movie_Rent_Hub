package com.jk.BackEndLocadora.exceptions;

public class LocacaoEmAtrasoExceededException extends RuntimeException {
    public LocacaoEmAtrasoExceededException(String message) {
        super(message);
    }
}