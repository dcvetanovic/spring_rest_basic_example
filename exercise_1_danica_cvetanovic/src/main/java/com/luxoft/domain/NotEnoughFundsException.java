package com.luxoft.domain;

public class NotEnoughFundsException extends Exception {
	private static final long serialVersionUID = 1L;

	private long id;
    private long balance;
    private long amount;

    public NotEnoughFundsException(long id, long balance, long amount) {
        this.id = id;
        this.balance = balance;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public long getAmount() {
        return amount;
    }
}
