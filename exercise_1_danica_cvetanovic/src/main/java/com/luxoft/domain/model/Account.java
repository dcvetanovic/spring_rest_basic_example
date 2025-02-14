package com.luxoft.domain.model;

import com.luxoft.domain.NotEnoughFundsException;

public class Account {

    private long id;
    private String holder;
    private long balance;

    public Account(String holder, long balance) {
        this.id = -1;
        this.holder = holder;
        this.balance = balance;
    }

    public Account(long id, String holder, long balance) {
        this.id = id;
        this.holder = holder;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public long getBalance() {
        return balance;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public void withdraw(long amount) throws NotEnoughFundsException {
        if (amount > balance) {
            throw new NotEnoughFundsException(id, balance, amount);
        }
        balance -= amount;
    }
}
