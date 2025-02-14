package com.luxoft.dao;

import com.luxoft.domain.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountDaoImpl implements AccountDao {

    private static final String SELECT_ALL_ACCOUNTS =
            "SELECT * FROM accounts";
    private static final String SELECT_ACCOUNT =
            "SELECT * FROM accounts WHERE accounts.id=:id";
    private static final String UPDATE_ACCOUNT =
            "UPDATE accounts SET holder=:holder, balance=:balance WHERE id=:id";
    private static final String DELETE_ACCOUNT =
            "DELETE FROM accounts WHERE id=:id";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcOperations namedJdbcTemplate;

    @Autowired
    public AccountDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcOperations namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Account get(long id) {
        return namedJdbcTemplate.query(
                SELECT_ACCOUNT, Collections.singletonMap("id", id), (resultSet, i) -> {
                    final long id1 = resultSet.getLong("id");
                    final String holder = resultSet.getString("holder");
                    final long balance = resultSet.getLong("balance");
                    return new Account(id1, holder, balance);
                }
        ).stream().findFirst().orElse(null);
    }

    @Override
    public Account insert(Account account) {
        Map<String, Object> params = new HashMap<>();
        params.put("holder", account.getHolder());
        params.put("balance", account.getBalance());
        long newId = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("accounts")
            .usingGeneratedKeyColumns("id")
            .executeAndReturnKey(params).longValue();
        account.setId(newId);
        return account;
    }

    @Override
    public void update(Account account) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", account.getId());
        params.put("holder", account.getHolder());
        params.put("balance", account.getBalance());
        namedJdbcTemplate.update(UPDATE_ACCOUNT, params);
    }

    @Override
    public void delete(long id) {
        namedJdbcTemplate.update(DELETE_ACCOUNT, Collections.singletonMap("id", id));
    }

    public List<Account> findAll() {
        return namedJdbcTemplate.query(
            SELECT_ALL_ACCOUNTS, (resultSet, i) -> {
                final long id1 = resultSet.getLong("id");
                final String holder = resultSet.getString("holder");
                final long balance = resultSet.getLong("balance");
                return new Account(id1, holder, balance);
            }
        );
    }

}
