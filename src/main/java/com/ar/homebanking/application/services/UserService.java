package com.ar.homebanking.application.services;

import com.ar.homebanking.domain.models.Account;

import com.ar.homebanking.domain.models.User;
import com.ar.homebanking.api.mappers.UserMapper;
import com.ar.homebanking.api.dtos.UserDto;
import com.ar.homebanking.infrastructure.repositories.AccountRepository;
import com.ar.homebanking.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ar.homebanking.domain.exceptions.UserNotFoundException;
import com.ar.homebanking.domain.exceptions.AccountNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AccountRepository AccountRepository;

    public UserService(UserRepository repository, AccountRepository accountRepository) {

        this.repository = repository;
        this.AccountRepository = accountRepository;
    }

    public List<UserDto> getUsers(){
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::userMapToDto)
                .toList();
    }

    public UserDto getUserById(Long id) throws UserNotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("No se encontró el usuario con el id: " + id));
        return UserMapper.userMapToDto(user);
    }

    public UserDto createUser(UserDto user) {
        return UserMapper.userMapToDto(repository.save(UserMapper.dtoToUser(user)));
    }

    public UserDto updateUser(Long id, UserDto user) {
        Optional<User> userCreated = repository.findById(id);
        if (userCreated.isPresent()){
            User entity = userCreated.get();
            User accountUpdated = UserMapper.dtoToUser(user);
            accountUpdated.setAccounts(entity.getAccounts());
            if (user.getIdAccounts() != null) {
                List <Account> accountList = AccountRepository.findAllById(user.getIdAccounts());
                List<Account> accountListFilter=accountList.stream().filter(e->!entity.getAccounts().contains(e)).toList();
                accountUpdated.getAccounts().addAll(accountListFilter);
                accountUpdated.setAccounts(accountList);
            }
            accountUpdated.setId(entity.getId());
            User saved = repository.save(accountUpdated);
            return UserMapper.userMapToDto(saved);
        } else {
            throw new AccountNotFoundException("No se encontró el usuario con el id: " + id);
        }
    }

    public String deleteUser(Long id){

        if (repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new UserNotFoundException("No se encontró el usuario con el id: " + id);
        }
        return ("Se ha eliminado el usuario con el id: " + id);
    }
  }

