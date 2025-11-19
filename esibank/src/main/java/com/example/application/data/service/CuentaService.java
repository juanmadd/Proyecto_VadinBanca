package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Noticia;
import com.example.application.data.entity.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    private final CuentaRepository repository;

    public CuentaService(CuentaRepository repository) {
        this.repository = repository;
    }

    public Optional<Cuenta> get(Long id) {
        return repository.findById(id);
    }
    
    public Cuenta update(Cuenta entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Cuenta> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Cuenta> list(Pageable pageable, Specification<Cuenta> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
    
    public List<String> getByUser(User us){
    	return repository.getByUser(us);
    }
    
    public Cuenta getByIban(String iban){
    	return repository.getByIban(iban);
    }
    
    public Cuenta getById(Long id) {
    	return repository.getById(id);
    }
    
    public List<Cuenta> getAllCuentas() {
        return repository.findAll();
    }

}
