package com.example.application.data.entity;

import com.example.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cuenta")
public class Cuenta extends AbstractEntity {
	
	private String iban;
	//private String titular; //USER
	private Float saldo;
	private LocalDate fechaCreacion;
	//private Integer tarjeta; //Tarjeta
	
	//Lista de movimientos Origen
	//Lista de movimientos Destino
	//Lista de movimientos Recibos
	
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	
	@OneToMany(mappedBy = "cuenta_id")
   	private List<RecibosDomiciliados> Recibos;
	
	@OneToMany(mappedBy = "cuenta") //CAMBIAR POR cuenta_id
   	private List<Tarjeta> Tarjetas;
	
	/*
	@OneToMany(mappedBy = "cuenta_origen")
	private List<Movimiento> MovimientosO;
	    
	@OneToMany(mappedBy = "cuenta_destino")
	private List<Movimiento> MovimientosD;
	*/
	
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	/*public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}*/
	public Float getSaldo() {
		return saldo;
	}
	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	public Cuenta(String iban, Float saldo, LocalDate fechaCreacion, User user) {
		super();
		this.iban = iban;
		this.saldo = saldo;
		this.fechaCreacion = fechaCreacion;
		this.user = user;
	}
	
	public Cuenta() {
		
	}
	
	
	
}
