package com.ar.homebanking.api.mappers;

import com.ar.homebanking.domain.models.Account;
import com.ar.homebanking.domain.models.User;
import com.ar.homebanking.api.dtos.UserDto;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {

    public User dtoToUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;

    }

    public UserDto userMapToDto(User user){
        UserDto dto = new UserDto();
        List<Long> accountsId=new ArrayList<>();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        if (user.getAccounts()!=null)
            for (Account a:user.getAccounts()) {
                Long id= a.getId();
                accountsId.add(id);
            }
        dto.setIdAccounts(accountsId);
        dto.setId(user.getId());
        return dto;
    }

}

