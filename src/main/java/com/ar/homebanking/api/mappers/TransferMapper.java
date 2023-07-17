package com.ar.homebanking.api.mappers;

import com.ar.homebanking.api.dtos.TransferDto;
import com.ar.homebanking.domain.models.Transfer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {

    public Transfer dtoToTransfer(TransferDto dto) {
        Transfer transfer = new Transfer();
        transfer.setOrigin(dto.getOrigin());
        transfer.setTarget(dto.getTarget());
        transfer.setDate(dto.getDate());
        transfer.setAmount(dto.getAmount());
        return transfer;

    }

    public TransferDto transferToDto(Transfer transfer) {
        TransferDto dto = new TransferDto();
        dto.setOrigin(transfer.getOrigin());
        dto.setTarget(transfer.getTarget());
        dto.setDate(transfer.getDate());
        dto.setAmount(transfer.getAmount());
        dto.setId(transfer.getId());

        return dto;

    }

}
