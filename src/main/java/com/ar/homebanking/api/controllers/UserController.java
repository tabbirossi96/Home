package com.ar.homebanking.api.controllers;

import java.util.List;

import com.ar.homebanking.api.dtos.UserDto;
import com.ar.homebanking.application.services.UserService;
import com.ar.homebanking.domain.exceptions.AccountNotFoundException;
import com.ar.homebanking.domain.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // GET
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers() {
           List<UserDto> usuarios = service.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    // GET USER
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDto userDto;
        try {
            userDto = service.getUserById(id);
        }
        catch (UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    // POST
    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));  //class30
    }

    // PUT
    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, user));
    }

    // DELETE
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        String user;
        //SI EL USUARIO EXISTE, ELIMINAR
        try {
            user = service.deleteUser(id);
        }
        //SI EL USUARIO NO EXISTE. AVISAR QUE NO EXISTE
        catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteUser(id));
    }
}