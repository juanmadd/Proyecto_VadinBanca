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
@Table(name = "recibo_domiciliado")
public class RecibosDomiciliados {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="concepto", nullable = false, length = 32)
	private String sConcepto;
	
	@Column(name="fecha", nullable = false)
	private Date dFechaDomiciliacion;
	
	@Column(name="valor", nullable = false)
	private Float fCantidad;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_id")
	private CuentaBancaria sCuenta;
	
	public Integer getId() {
		return id;
	}
	/*public void setId(Integer id) {
		this.id = id;
	}*/
	public String getsConcepto() {
		return sConcepto;
	}
	public void setsConcepto(String sConcepto) {
		if(sConcepto == null) throw new IllegalArgumentException("El campo concepto no puede estar vacío.");
		else this.sConcepto = sConcepto;
	}
	public Date getdFechaDomiciliacion() {
		return dFechaDomiciliacion;
	}
	public void setdFechaDomiciliacion(Date dFechaDomiciliacion) {
		this.dFechaDomiciliacion = dFechaDomiciliacion;
	}
	public Float getfCantidad() {
		return fCantidad;
	}
	public void setfCantidad(Float fCantidad) {
		this.fCantidad = fCantidad;
	}
	public CuentaBancaria getsCuenta() {
		return sCuenta;
	}
	public void setsCuenta(CuentaBancaria sCuenta) {
		if(sCuenta == null) throw new IllegalArgumentException("El campo cuenta no puede estar vacío.");
		else this.sCuenta = sCuenta;
	}
	
	public RecibosDomiciliados(Date dFechaDomiciliacion, String sConcepto, Float fCantidad, CuentaBancaria cuenta){ this(null, dFechaDomiciliacion, sConcepto, fCantidad, cuenta); }
	
	private RecibosDomiciliados(Integer iId, Date dFechaDomiciliacion, String sConcepto, Float fCantidad, CuentaBancaria cuenta){
		setsConcepto(sConcepto);
		setdFechaDomiciliacion(dFechaDomiciliacion);
		setfCantidad(fCantidad);
		setsCuenta(cuenta);
		id = iId;
	}
	
}
