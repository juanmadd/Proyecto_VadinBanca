package Test;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.application.data.Role;
import com.example.application.data.entity.User;
import com.example.application.data.service.UserRepository;
import com.example.application.data.service.UserService;

public class UserTest {
	@PersistenceContext(name = "UnidadUser")
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadUser");
	@PersistenceContext(name = "UnidadUser")
	EntityManager em = emf.createEntityManager();
	
	@Autowired
	private UserService userServ;
	
	@Test
	public void insertarUsuario() {
				
		User us = new User();
		us.setDni("11111111A");
		us.setEmail("juanmadiaz@gmail.com");
		us.setFechaNacimiento(new Date(99, 8, 3));
		us.setHashedPassword("Contrasena");
		us.setLastName("Diaz Diaz");
		us.setName("Juanma");
		us.setRoles(Role.USER);
		us.setProfilePicture("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png");
			
		try {
			em.getTransaction().begin();
			em.persist(us);
			em.getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	
	}
	
	@Test
	public void insertarUsuarioJPA() {
		User us = new User();
		us.setDni("22222222B");
		us.setEmail("gestor@gmail.com");
		us.setFechaNacimiento(new Date(90, 7, 22));
		us.setHashedPassword("Contrasena2");
		us.setLastName("Garcia Garcia");
		us.setName("Pepe");
		us.setRoles(Role.GESTOR);
		us.setProfilePicture("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png");
		
		User us2 = userServ.add(us);
		
		assertNotNull(us2);
	}
}
