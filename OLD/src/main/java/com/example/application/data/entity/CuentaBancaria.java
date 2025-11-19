package com.example.application.data.entity;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancaria {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, length = 24)
	private String iban;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User titular; //Como se relaciona con el usuario
    
	@Column(name = "saldo")
    private Float saldo;
    
    @Column (name = "fecha_creacion", nullable = false)
    private Date dFechaCreacion;
    
    @OneToMany(mappedBy = "cuentaOrigen")
	private List<Movimiento> MovimientosO;
    
    @OneToMany(mappedBy = "cuentaDestino")
   	private List<Movimiento> MovimientosD;
    
    @OneToMany(mappedBy = "cuenta")
   	private List<Tarjeta> Tarjetas;
    
    @OneToMany(mappedBy = "sCuenta")
   	private List<RecibosDomiciliados> Recibos;
   
	public Integer getId() {
		return id;
	}
	/*public void setId(Integer id) {
		this.id = id;
	}*/
	
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		if(iban == null) throw new IllegalArgumentException("El campo iban no puede estar vacío.");
		else this.iban = iban;
	}
	
	public User getTitular() {
		return titular;
	}
	public void setTitular(User titular) {
		if(titular == null) throw new IllegalArgumentException("El campo titular no puede estar vacío.");
		else this.titular = titular;
	}
	
	public Float getSaldo() {
		return saldo;
	}
	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}
	
	public Date getdFechaCreacion() {
		return dFechaCreacion;
	}
	public void setdFechaCreacion(Date dFechaCreacion) {
		this.dFechaCreacion = dFechaCreacion;
	}
	
	//Constructores
	
		public CuentaBancaria(String sIban, User titular, Float fSaldo, Date dFechaCreacion){ this(null, sIban, titular, fSaldo, dFechaCreacion); }
			
		private CuentaBancaria(Integer iId, String sIban, User titular, Float fSaldo, Date dFechaCreacion){
			setIban(sIban);
			setTitular(titular);
			setSaldo(fSaldo);
			setdFechaCreacion(dFechaCreacion);
			id = iId;
		}
    
}
