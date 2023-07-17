package com.ar.homebanking.infrastructure.repositories;

import com.ar.homebanking.domain.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

   // public List<Account> findAllById

 //TODO: AGREGAR UNA BUSQUEDA POR NUMERO DE CUENTQA EN LA BASE DE DATOS

  /*  @Query(nativeQuery = true, value = "SELECT*FROM account WHERE nombre = :number")
    public Account getAccountByNumber(@Param("number") int number);
*/ //SE BORRA, YA NO VA ASER NECESARIO, NOS MANEJAMOS CON LOS CRUD
    }
