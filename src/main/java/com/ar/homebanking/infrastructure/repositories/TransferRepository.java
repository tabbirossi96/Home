package com.ar.homebanking.infrastructure.repositories;

import com.ar.homebanking.domain.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
