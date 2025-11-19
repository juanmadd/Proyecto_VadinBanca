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
@Table(name = "movimiento")
public class Movimiento {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "fecha" ,nullable = false)
	private Date dFecha;
	
	@Column(name = "valor")
	private Float fValor;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_origen")
	private CuentaBancaria cuentaOrigen;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_destino")
	private CuentaBancaria cuentaDestino; //Si recibes dinero, tu cuenta es la destino.
	
	public Integer getId() {
		return id;
	}
	/*public void setId(Integer id) {
		this.id = id;
	}*/
	
	public Date getdFecha() {
		return dFecha;
	}
	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}
	
	public Float getfValor() {
		return fValor;
	}
	public void setfValor(Float fValor) {
		this.fValor = fValor;
	}
	
	public CuentaBancaria getCuentaOrigen() {
		return cuentaOrigen;
	}
	public void setCuentaOrigen(CuentaBancaria cuentaOrigen) {
		if(cuentaOrigen == null) throw new IllegalArgumentException("El campo cuentaOrigen no puede estar vacío.");
		this.cuentaOrigen = cuentaOrigen;
	}
	
	public CuentaBancaria getCuentaDestino() {
		return cuentaDestino;
	}
	public void setCuentaDestino(CuentaBancaria cuentaDestino) {
		if(cuentaOrigen == null) throw new IllegalArgumentException("El campo cuentaDestino no puede estar vacío.");
		this.cuentaDestino = cuentaDestino;
	}
	
	//Constructores
	
	public Movimiento(Date dFecha, Float fValor, CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino){ this(null, dFecha, fValor, cuentaOrigen, cuentaDestino); }
		
	private Movimiento(Integer iId, Date dFecha, Float fValor, CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino){
		setdFecha(dFecha);
		setfValor(fValor);
		setCuentaOrigen(cuentaOrigen);
		setCuentaDestino(cuentaDestino);
		id = iId;
	}
	
	
}