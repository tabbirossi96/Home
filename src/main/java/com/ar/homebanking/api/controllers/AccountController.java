package com.ar.homebanking.api.controllers;

import com.ar.homebanking.api.dtos.AccountDto;
import com.ar.homebanking.application.services.AccountService;
import com.ar.homebanking.domain.exceptions.AccountNotFoundException;
import com.ar.homebanking.domain.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService service;


    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    // GET
    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts() {
             List<AccountDto> Accounts = service.getAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(Accounts);
    }
    //GET ACCOUNT
    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        AccountDto accountDto;
        try {
            accountDto = service.getAccountById(id);
        }
        catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }
    // POST
    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(dto));
    }

    // PUT
    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto account){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(id, account));
    }

    // DELETE
    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
       String accountDto;
        try {
            accountDto = service.deleteAccount(id);
        }
        catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }

}
