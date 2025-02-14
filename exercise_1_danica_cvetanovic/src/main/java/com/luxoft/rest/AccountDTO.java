/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package com.luxoft.rest;

import com.luxoft.domain.model.Account;

/**
 * DTO that represents Account
 */
@SuppressWarnings("all")
public class AccountDTO {

    private long id = -1;
    private String holder;
    private long balance;

    public AccountDTO() {
    }

    public AccountDTO(long id, String holder, long balance) {
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

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public static Account toDomainObject(AccountDTO dto) {
        return new Account(dto.getId(), dto.getHolder(), dto.getBalance());
    }

    public static AccountDTO toDto(Account account) {
        return new AccountDTO(account.getId(), account.getHolder(), account.getBalance());
    }
}
