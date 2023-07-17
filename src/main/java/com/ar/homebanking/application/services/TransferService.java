package com.ar.homebanking.application.services;

import com.ar.homebanking.api.dtos.TransferDto;
import com.ar.homebanking.api.mappers.TransferMapper;
import com.ar.homebanking.application.exceptions.InsufficientFundsException;
import com.ar.homebanking.domain.exceptions.TransferNotFoundException;
import com.ar.homebanking.domain.models.Account;
import com.ar.homebanking.domain.models.Transfer;
import com.ar.homebanking.infrastructure.repositories.AccountRepository;
import com.ar.homebanking.infrastructure.repositories.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ar.homebanking.domain.exceptions.AccountNotFoundException;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

        @Autowired
        private TransferRepository repository;

        @Autowired
        private AccountRepository accountRepository;

        public TransferService(TransferRepository repository, AccountRepository accountRepository) {
            this.repository = repository;
            this.accountRepository = accountRepository;
        }

    public List<TransferDto> getTransfers(){
        List<Transfer> transfers = repository.findAll();
        return transfers.stream().map(TransferMapper::transferToDto)
                .collect(Collectors.toList());
    }

     public TransferDto getTransferById(Long id) {
        Transfer transfer = repository.findById(id).orElseThrow(() ->
                new TransferNotFoundException("Transfer not found with id: " + id));
        return TransferMapper.transferToDto(transfer);
    }

    public TransferDto updateTransfer(Long id, TransferDto transferDto){
        Transfer transfer = repository.findById(id).orElseThrow(() ->
                new TransferNotFoundException("No se encontró la transferencia con el id: " + id));
                Transfer updatedTransfer = TransferMapper.dtoToTransfer(transferDto);
        updatedTransfer.setId(transfer.getId());
        return TransferMapper.transferToDto(repository.save(updatedTransfer));
    }


   /* public String deleteTransfer(Long id){

        if (repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new TransferNotFoundException("No se encontró la transferencia con el id: " + id);
        }
        return ("Se ha eliminado la transferencia con el id: " + id);
    }*/
   public String deleteTransfer(Long id){
       if (repository.existsById(id)){
           repository.deleteById(id);
           return "Se ha eliminado la transferencia";
       } else {
           return "No se ha eliminado la transferencia";
       }
   }

    @Transactional
    public TransferDto performTransfer(TransferDto dto) {

        //VALIDACIONES NECESARIAS PARA HACER LA TRANSFERENCIA----------------------------------------------------------

        // Que las cuentas existan
        Account originAccount = accountRepository.findById(dto.getOrigin())
                .orElseThrow(() -> new AccountNotFoundException("No se encontró la cuenta con el id: " + dto.getOrigin()));
        Account destinationAccount = accountRepository.findById(dto.getTarget())
                .orElseThrow(() -> new AccountNotFoundException("No se encontró la cuenta con el id: " + dto.getTarget()));
        // Que tengan dinero
        if (originAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFundsException("Fondos insuficientes en la cuenta con el id: " + dto.getOrigin());        }
        // Hacer la transferencia
        originAccount.setBalance(originAccount.getBalance().subtract(dto.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(dto.getAmount()));
        // Guardar las cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        // LLEVAR ACABO LA TRANSFERENCIA-------------------------------------------------------------------------------

        // Crear la transferencia y guardarla en la BD
        Transfer transfer = new Transfer();
        // Crear un objeto Date para obtener la fecha actual
        Date date = new Date();
        // Setteamos el objeto Date en el transferDto
        transfer.setDate(date);
        transfer.setOrigin(originAccount.getId());
        transfer.setTarget(destinationAccount.getId());
        transfer.setAmount(dto.getAmount());
        transfer = repository.save(transfer);
        // Devolver el DTO de la transferencia realizada
        return TransferMapper.transferToDto(transfer);
    }

}
