package com.example.application.data.entity;

import com.example.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "movimiento")
public class Movimiento extends AbstractEntity {
	private String concepto;
	private LocalDate dFecha;
	private Double fValor;
	private String cuentaOrigen; 
	private String cuentaDestino;	
	
	
	/*
	@ManyToOne
    @JoinColumn(name="cuenta_origen")
    private Cuenta cuenta_origen;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_destino")
	private Cuenta cuenta_destino; //Si recibes dinero, tu cuenta es la destino.
	*/
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public LocalDate getdFecha() {
		return dFecha;
	}
	public void setdFecha(LocalDate dFecha) {
		this.dFecha = dFecha;
	}
	public Double getfValor() {
		return fValor;
	}
	public void setfValor(Double fValor) {
		this.fValor = fValor;
	}
		
	
	public Movimiento(String concepto, LocalDate dFecha, Double fValor, String cuenta_origen, String cuenta_destino) {
		super();
		this.concepto = concepto;
		this.dFecha = dFecha;
		this.fValor = fValor;
		this.cuentaOrigen = cuenta_origen;
		this.cuentaDestino = cuenta_destino;
		//this.cuenta_origen = cuenta_origen;
		//this.cuenta_destino = cuenta_destino;
	}
	
	public Movimiento() {
		
	}
	
	public String getCuentaOrigen() {
		return cuentaOrigen;
	}
	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	public String getCuentaDestino() {
		return cuentaDestino;
	}
	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
	
}
