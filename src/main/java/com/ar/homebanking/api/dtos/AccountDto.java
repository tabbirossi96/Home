package com.ar.homebanking.api.dtos;

import com.ar.homebanking.domain.models.User;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class AccountDto {

    private Long id;

    private BigDecimal amount;

    private UserDto owner;

}
