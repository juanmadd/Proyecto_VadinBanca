package com.example.application.data.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tarjeta")
public class Tarjeta {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, length = 24)
	private String numero;
	
	@Column(nullable = false, length = 32)
	private String emisor; //Visa, MasterCard y American Express.
	
	@ManyToOne
	@JoinColumn(name = "cuenta_id")
	private CuentaBancaria cuenta;
	
	@Column(nullable = false)
	private Date fecha_caducidad;
	
	@Column(nullable = false)
	private Integer pin;
	
	@Column(nullable = false)
	private Integer cvv;
	
	@Column
	private Float limite_maximo;
	
	@Column
	private Float limite_minimo;
	
	public Integer getId() {
		return id;
	}
	/*public void setId(Integer id) {
		this.id = id;
	}*/
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		if(numero == null) throw new IllegalArgumentException("El campo numero no puede estar vacío.");
		this.numero = numero;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		if(emisor == "MasterCard" || emisor == "American Express" || emisor == "Visa") this.emisor = emisor;
		else throw new IllegalArgumentException("Error, emisor de tarjeta es incorrecto");
	}
	public CuentaBancaria getCuenta() {
		return cuenta;
	}
	public void setCuenta(CuentaBancaria cuenta) {
		if(cuenta == null) throw new IllegalArgumentException("El campo cuenta no puede estar vacío.");
		else this.cuenta = cuenta;
	}
	public Date getFechaCaducidad() {
		return fecha_caducidad;
	}
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fecha_caducidad = fechaCaducidad;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		if(pin < 0 || pin > 9999) throw new IllegalArgumentException("El campo pin es incorrecto.");
		else this.pin = pin;
	}
	public Integer getCvv() {
		return cvv;
	}
	public void setCvv(Integer cvv) {
		if(cvv < 0 || cvv > 999) throw new IllegalArgumentException("El campo cvv es incorrecto.");
		this.cvv = cvv;
	}
	public Float getLimiteMax() {
		return limite_maximo;
	}
	public void setLimiteMax(Float limiteMax) {
		this.limite_maximo = limiteMax;
	}
	public Float getLimiteMin() {
		return limite_minimo;
	}
	public void setLimiteMin(Float limiteMin) {
		this.limite_minimo = limiteMin;
	}
	
	public Tarjeta(String iNumero, String sEmisor, CuentaBancaria cuenta, Date dFechaCaducidad, Integer iPin, Integer iCvv, Float fLimiteMin, Float fLimiteMax){ this(null, iNumero, sEmisor, cuenta, dFechaCaducidad, iPin, iCvv, fLimiteMin, fLimiteMax); }
	
	private Tarjeta(Integer iId, String iNumero, String sEmisor, CuentaBancaria cuenta, Date dFechaCaducidad, Integer iPin, Integer iCvv, Float fLimiteMin, Float fLimiteMax){
		setNumero(iNumero);
		setEmisor(sEmisor);
		setCuenta(cuenta);
		setFechaCaducidad(dFechaCaducidad);
		setPin(iPin);
		setCvv(iCvv);
		setLimiteMax(fLimiteMax);
		setLimiteMin(fLimiteMin);
		id = iId;
	}
	
}
