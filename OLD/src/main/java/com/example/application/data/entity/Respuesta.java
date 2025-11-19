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
@Table(name = "respuesta")
public class Respuesta {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="fecha", nullable = false)
	private Date dFechaRealizacion;
	
	@Column(name="contenido", nullable = false, length = 255)
	private String sContenido;
	
	@ManyToOne
	@JoinColumn(name = "consulta_id")
	private Consulta consulta;
	
	@ManyToOne
	@JoinColumn(name = "gestor_id")
	private User gestor;	//Gestor al que se le envia la consulta
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;	//Gestor al que se le envia la consulta
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Consulta getConsulta() {
		return consulta;
	}
	public void setConsulta(Consulta consulta) {
		if(consulta == null) throw new IllegalArgumentException("El campo contenido no puede estar vacío.");
		else this.consulta = consulta;
	}
	public User getGestor() {
		return gestor;
	}
	public void setGestor(User gestor) {
		if(gestor == null) throw new IllegalArgumentException("El campo contenido no puede estar vacío.");
		else this.gestor = gestor;
	}
	
	public Respuesta(Date dFechaRealizacion, String sContenido, Consulta consulta, User gestor, User user){ this(null, dFechaRealizacion, sContenido, consulta, gestor, user); }
	
	private Respuesta(Integer iId, Date dFechaRealizacion, String sContenido, Consulta consulta, User gestor, User user) {
		setdFechaRealizacion(dFechaRealizacion);
		setsContenido(sContenido);
		setConsulta(consulta);
		setGestor(gestor);
		setUser(user);
		id = iId;
	}
	

}
