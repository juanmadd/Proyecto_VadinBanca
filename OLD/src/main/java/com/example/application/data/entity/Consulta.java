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
@Table(name = "consulta")
public class Consulta {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "fecha", nullable = false)
	private Date dFechaRealizacion;
	
	@Column(name = "contenido", nullable = false, length = 255)
	private String sContenido;
	
	@ManyToOne
	@JoinColumn(name = "gestor_id")
	private User gestor;	//Gestor al que se le envia la consulta
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User cliente;
	
	@OneToMany(mappedBy = "consulta")
	 private List<Respuesta> Respuestas;
	
	public User getCliente() {
		return cliente;
	}
	public void setCliente(User cliente) {
		this.cliente = cliente;
	}
	public Integer getId() {
		return id;
	}
	/*public void setId(Integer id) {
		this.id = id;
	}*/
	public Date getdFechaRealizacion() {
		return dFechaRealizacion;
	}
	public void setdFechaRealizacion(Date dFechaRealizacion) {
		this.dFechaRealizacion = dFechaRealizacion;
	}
	public String getsContenido() {
		return sContenido;
	}
	public void setsContenido(String sContenido) {
		if(sContenido == null) throw new IllegalArgumentException("El campo contenido no puede estar vacío.");
		else this.sContenido = sContenido;
	}
	public User getGestor() {
		return gestor;
	}
	public void setGestor(User gestor) {
		if(gestor == null) throw new IllegalArgumentException("El campo gestor no puede estar vacío.");
		else this.gestor = gestor;
	}
	
	public Consulta(Date dFechaRealizacion, String sContenido, User gestor, User cliente){ this(null, dFechaRealizacion, sContenido, gestor, cliente); }
	
	private Consulta(Integer iId, Date dFechaRealizacion, String sContenido, User gestor, User cliente) {
		setdFechaRealizacion(dFechaRealizacion);
		setsContenido(sContenido);
		setGestor(gestor);
		setCliente(cliente);
		id = iId;
	}

}
