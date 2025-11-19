package com.example.application.data.entity;

import com.example.application.data.Role;
import com.example.application.data.service.UserRepository;
import com.example.application.data.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class User {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "lastName", nullable = false, length = 100)
    private String lastName;
	
	@Column(name = "name", nullable = false, length = 100)
    private String name;
	
	@Column(name = "email", nullable = false, length = 128)
    private String email;
	
	@Column(name = "dni", nullable = false, length = 9)
    private String dni;
	
	@Column(name = "fechaNacimiento", nullable = false)
    private Date fechaNacimiento;
	
    @JsonIgnore
    @Column(name="password", nullable = false, length = 128)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name="roles", nullable = false)
    private Role roles;
    
    @OneToMany(mappedBy = "titular")
   	private List<CuentaBancaria> Cuentas;
    
    @Lob
    @Column(name = "profile_picture", length = 128)
    private String profile_picture;
    
    @OneToMany(mappedBy = "cliente")
   	private List<Consulta> ConsultasC;
    
    @OneToMany(mappedBy = "gestor")
   	private List<Consulta> ConsultasG;
    
    @OneToMany(mappedBy = "user")
   	private List<Respuesta> RespuestasC;
    
    @OneToMany(mappedBy = "gestor")
   	private List<Respuesta> RespuestasG;


        
    public User() {
    }

    public int getid() {
    	return id;
    }
    
    /*public void setId() {
    	this.id = UserService.lastId();
	}*/
    
    public String getDni() {
		return dni;
	}
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setDni(String dni) {
		if(dni == null) throw new IllegalArgumentException("El campo dni no puede estar vacío.");
		else this.dni = dni;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
    	if(lastName == null) throw new IllegalArgumentException("El campo username no puede estar vacío.");
		else this.lastName = lastName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
    	if(name == null) throw new IllegalArgumentException("El campo name no puede estar vacío.");
		else this.name = name;
    }
    public String getHashedPassword() {
        return password;
    }
    public void setHashedPassword(String password) {
    	if(password == null) throw new IllegalArgumentException("El campo hashedPassword no puede estar vacío.");
		else this.password = password;
    }
    public Role getRoles() {
        return roles;
    }
    public void setRoles(Role roles) {
        this.roles = roles;
    }
    public String getProfilePicture() {
        return profile_picture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profile_picture = profilePicture;
    }
        
    public User(String username, String name, String email, String dni, Date fechaNac, String hashedPass, Role rol, String profilePicture) {
    	this.id=(Integer) null;
    	setLastName(username);
    	setName(name);
    	setEmail(email);
    	setDni(dni);
    	setFechaNacimiento(fechaNac);
    	setHashedPassword(hashedPass);
    	setRoles(rol);
    	setProfilePicture(profilePicture);
    }
    
    //public User(String username, String name, String email, String dni, Date fechaNac, String hashedPass, Role user, String profilePicture) {this(, username, name, email, dni, fechaNac, hashedPass, user, profilePicture);}

}