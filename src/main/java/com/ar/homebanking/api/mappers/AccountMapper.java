package com.ar.homebanking.api.mappers;

import com.ar.homebanking.api.dtos.AccountDto;
import com.ar.homebanking.api.dtos.UserDto;
import com.ar.homebanking.domain.models.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    public Account dtoToAccount(AccountDto dto) { //algo que me muestre las cuentas que tiene un usuario cuando lo consulto
        Account account = new Account();
        account.setBalance(dto.getAmount());
        //account.setOwner(dto.getOwner()); ---- ahora volvemos a esto
        return account;

    }


    public AccountDto AccountToDto(Account account){
        AccountDto dto = new AccountDto();
        dto.setAmount(account.getBalance());
        if (account.getOwner()!=null){
            UserDto userDto=UserMapper.userMapToDto(account.getOwner());
            dto.setOwner(userDto);
        }
        dto.setId(account.getId());
        return dto;
    }
}
