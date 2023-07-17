package com.ar.homebanking.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {


    private Long id;

    private String username;

    private String password;

     private List<Long> idAccounts;

}
