package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long>, JpaSpecificationExecutor<Movimiento> {

	Movimiento findById(Integer Id);
	
	/*@Query("SELECT mov FROM Movimiento mov WHERE :filter AND mov.cuentaOrigen = :cuenta OR mov.cuentaDestino = :cuenta")
	Page<Movimiento> findByCuenta(Specification<Movimiento> filter, org.springframework.data.domain.Pageable pageable, String cuenta);*/
}
