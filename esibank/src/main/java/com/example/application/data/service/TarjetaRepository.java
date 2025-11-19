package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long>, JpaSpecificationExecutor<Tarjeta> {

    Tarjeta findByNumero(Integer numero);
    
    @Query("SELECT ta FROM Tarjeta ta WHERE ta.cuenta = :cuenta")
    List<Tarjeta> getTarjetasByCuenta(@Param("cuenta") Cuenta cuenta);
    
    @Query("SELECT ta FROM Tarjeta ta WHERE ta.numero = :numero")
    public Tarjeta getByNumber(@Param("numero") String numero);
}
