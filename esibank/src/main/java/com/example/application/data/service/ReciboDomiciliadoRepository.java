package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.RecibosDomiciliados;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReciboDomiciliadoRepository extends JpaRepository<RecibosDomiciliados, Long>, JpaSpecificationExecutor<RecibosDomiciliados> {

	RecibosDomiciliados findById(Integer Id);
}
