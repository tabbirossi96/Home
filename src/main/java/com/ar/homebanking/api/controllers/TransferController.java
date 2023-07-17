package com.ar.homebanking.api.controllers;


import com.ar.homebanking.api.dtos.TransferDto;
import com.ar.homebanking.application.services.TransferService;
import com.ar.homebanking.domain.exceptions.TransferNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferController {

    private final TransferService service;

    @Autowired
    public TransferController(TransferService service) {
        this.service = service;
    }


    //GET
    @GetMapping(value = "/transfers")
    public ResponseEntity<List<TransferDto>> getTransfers(){
        List<TransferDto> transfers = service.getTransfers();
        return ResponseEntity.status(HttpStatus.OK).body(transfers);
    }

    //GET TRANSFERENCIA
    @GetMapping(value = "/transfers/{id}")
    public ResponseEntity<?> getTransferById(@PathVariable Long id){
        TransferDto transfer;
        try {
            transfer = service.getTransferById(id);
        }
        catch (TransferNotFoundException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    return ResponseEntity.status(HttpStatus.OK).body(transfer);
    }

    //POST
    @PostMapping(value = "/transfers")
    public ResponseEntity<TransferDto> performTransfer(@RequestBody TransferDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.performTransfer(dto));
    }

    //PUT
    @PutMapping(value = "/transfers/{id}")
    public ResponseEntity<TransferDto> updateTransfer(@PathVariable Long id, @RequestBody TransferDto transfer){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTransfer(id, transfer));
    }

    // DELETE
    @DeleteMapping(value = "/transfer/{id}")
    public ResponseEntity<?> deleteTransfer(@PathVariable Long id) {
        String transfer;
        try {
            transfer = service.deleteTransfer(id);
        }
        catch (TransferNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteTransfer(id));
    }

}
