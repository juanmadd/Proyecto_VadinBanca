package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Tarjeta;
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
public class TarjetaService {

    private final TarjetaRepository repository;

    public TarjetaService(TarjetaRepository repository) {
        this.repository = repository;
    }

    @Cacheable("tarjeta")
    public Optional<Tarjeta> get(Long id) {
        return repository.findById(id);
    }

    public Tarjeta update(Tarjeta entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Cacheable("tarjeta")
    public Page<Tarjeta> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable("tarjeta")
    public Page<Tarjeta> list(Pageable pageable, Specification<Tarjeta> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
    
    @Cacheable("tarjeta")
    public List<Tarjeta> getTarjetasByCuenta(Cuenta cuenta){
    	return repository.getTarjetasByCuenta(cuenta);
    }
    
    @Cacheable("tarjeta")
    public Tarjeta getByNumber(String numero) {
    	return repository.getByNumber(numero);
    }

}
