package com.luxoft.rest;

import com.luxoft.domain.AccountNotFoundException;
import com.luxoft.domain.AccountService;
import com.luxoft.domain.NotEnoughFundsException;
import com.luxoft.domain.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = service.findAll();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Account account = service.get(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
            //  return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable long id) {
        Account account = service.get(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
            //  return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AccountDTO.toDto(account));
    }


    @PostMapping("/")
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        Account account = AccountDTO.toDomainObject(dto);
        Account accountWithId = service.create(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AccountDTO.toDto(accountWithId));
    }


    @PutMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable("id") long id, @RequestParam("amount") long amount) {
        service.deposit(id, amount);
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable("id") long id, @RequestParam("amount") long amount) throws NotEnoughFundsException {
        service.withdraw(id, amount);
        return ResponseEntity.ok(service.get(id));
    }

    @ExceptionHandler({NotEnoughFundsException.class})
    public ResponseEntity<String> handleNotEnoughFunds(NotEnoughFundsException ex) {
        return ResponseEntity.badRequest()
                .body("Not enough funds (" + ex.getBalance() + ") on account " + ex.getId() + " to withdraw " + ex.getAmount());
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<String> accountNotFound(AccountNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header("name", "value")
                .body(ex.getMessage());
    }


}
