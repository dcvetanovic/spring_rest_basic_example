package com.luxoft.domain;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(long id) {
        super("Account with id: " + id + " not found");
    }
}
