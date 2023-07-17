package com.ar.homebanking.application.services;

import com.ar.homebanking.api.dtos.AccountDto;
import com.ar.homebanking.api.mappers.AccountMapper;
import com.ar.homebanking.domain.models.Account;
import com.ar.homebanking.domain.models.User;
import com.ar.homebanking.infrastructure.repositories.AccountRepository;
import com.ar.homebanking.infrastructure.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ar.homebanking.domain.exceptions.AccountNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private UserRepository userRepository;

    public AccountService(AccountRepository repository,UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<AccountDto> getAccounts() {
        List<Account> Accounts = repository.findAll();
        return Accounts.stream().map(AccountMapper::AccountToDto).toList();
    }

    @Transactional
    public AccountDto getAccountById(Long id) throws AccountNotFoundException{
        Account account = repository.findById(id).orElseThrow(() -> new AccountNotFoundException("No se encontró la cuenta con el id: " + id));
        return AccountMapper.AccountToDto(account);
    }

    @Transactional
    public AccountDto createAccount(AccountDto account){
        Optional<User> user=userRepository.findById(account.getOwner().getId());
        Account accountModel=AccountMapper.dtoToAccount(account);
        accountModel.setOwner(user.get());
        accountModel=repository.save(accountModel);
        AccountDto dto=AccountMapper.AccountToDto(accountModel);
        return dto;
    }

    @Transactional
    public AccountDto updateAccount(Long id, AccountDto account){
        Optional<Account> accountCreated = repository.findById(id);

        if (accountCreated.isPresent()){
            Account entity = accountCreated.get();
            if (account.getAmount()!=null) {
                entity.setBalance(account.getAmount());
            }
            if (account.getOwner()!=null){
                User user=userRepository.getReferenceById(account.getOwner().getId());
                if (user!=null){
                    entity.setOwner(user);
                }
            }
            Account saved = repository.save(entity);

            return AccountMapper.AccountToDto(saved);
        } else {
            throw new AccountNotFoundException("No se encontró la cuenta con el id: " + id);
        }
    }
    @Transactional

    public String deleteAccount(Long id) throws AccountNotFoundException{

        if (repository.existsById(id)){
            repository.deleteById(id);
            return ("Se ha eliminado la cuenta con el id: " + id);
        } else {
          throw new AccountNotFoundException("No se encontró la cuenta con el id: " + id);
        }

    }
}
