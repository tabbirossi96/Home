package com.ar.homebanking.infrastructure.repositories;


import com.ar.homebanking.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {




}
