package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaRepository extends JpaRepository<Cuenta, Long>, JpaSpecificationExecutor<Cuenta> {

    User findByIban(String iban);
    
    @Query("SELECT cu.iban FROM Cuenta cu WHERE cu.user = :us")
    public List<String> getByUser(@Param("us") User us);
    
    @Query("SELECT cu FROM Cuenta cu WHERE cu.iban = :iban")
    public Cuenta getByIban(@Param("iban") String iban);
    
    @Query("SELECT cu FROM Cuenta cu WHERE cu.id = :id")
    public Cuenta getById(@Param("id") Long id);
}
