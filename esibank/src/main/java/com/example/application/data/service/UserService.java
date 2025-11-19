package com.example.application.data.service;

import com.example.application.data.entity.Noticia;
import com.example.application.data.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Cacheable("application_user")
    public Optional<User> get(Long id) {
        return repository.findById(id);
    }
    
    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Cacheable("application_user")
    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable("application_user")
    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    @Cacheable("application_user")
	public Object findByUsername(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Cacheable("application_user")
	public List<User> getAllUsers() {
        return repository.findAll();
    }
	
	@Cacheable("application_user")
	public User getById(Long id) {
		return repository.getById(id);
	}

}
