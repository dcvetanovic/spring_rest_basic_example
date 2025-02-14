package com.luxoft.domain;

import com.luxoft.dao.AccountDao;
import com.luxoft.domain.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao dao;

    @Autowired
    public AccountServiceImpl(AccountDao dao) {
        this.dao = dao;
    }

    @Override
    public Account get(long id) {
        return dao.get(id);
    }

    @Override
    public List<Account> findAll() {
        return dao.findAll();
    }

    @Override
    public Account create(Account account) {
        return dao.insert(account);
    }

    @Override
    public void delete(long accountId) {
        dao.delete(accountId);
    }

    @Override
    public void deposit(long accountId, long amount) {
        Account account = dao.get(accountId);
        account.deposit(amount);
        dao.update(account);
    }

    @Override
    public void withdraw(long accountId, long amount) throws NotEnoughFundsException {
        Account account = dao.get(accountId);
        account.withdraw(amount);
        dao.update(account);
    }

    @Override
    public void changeHolder(long accountId, String newHolder) {
        Account account = dao.get(accountId);
        account.setHolder(newHolder);
        dao.update(account);
    }
}
