package com.example.application.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recibosDomiciliados")
public class RecibosDomiciliados extends AbstractEntity {
	
	//Nº de recibo es el ID de AbstractEntity
	//Para acceder al IBAN de la cuenta accedemos mediante el repositorio.
	private String concepto;
	private Date fecha;
	private Float valor;
	private String nombreEmisor;
	private String ibanEmisor;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_id")
	private Cuenta cuenta_id;
	
	public RecibosDomiciliados(String concepto, Date fecha, Float valor, String nombre_emisor, String iban_emisor) {
		super();
		this.concepto = concepto;
		this.fecha = fecha;
		this.valor = valor;
		this.ibanEmisor = nombre_emisor;
		this.nombreEmisor = iban_emisor;
	}
	
	public RecibosDomiciliados() {};
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}	
	public String getEntidad() {
		return ibanEmisor;
	}
	public void setEntidad(String entidad) {
		this.ibanEmisor = entidad;
	}
	public String getIbanEmisor() {
		return nombreEmisor;
	}
	public void setIbanEmisor(String ibanEmisor) {
		this.nombreEmisor = ibanEmisor;
	}
}
