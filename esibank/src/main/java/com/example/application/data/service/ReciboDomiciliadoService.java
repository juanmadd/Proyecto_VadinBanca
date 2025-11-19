package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Movimiento;
import com.example.application.data.entity.RecibosDomiciliados;
import com.example.application.data.entity.Tarjeta;
import com.example.application.data.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReciboDomiciliadoService {

    private final ReciboDomiciliadoRepository repository;

    public ReciboDomiciliadoService(ReciboDomiciliadoRepository repository) {
        this.repository = repository;
    }

    @Cacheable("recibosDomiciliados")
    public Optional<RecibosDomiciliados> get(Long id) {
        return repository.findById(id);
    }

    public RecibosDomiciliados update(RecibosDomiciliados entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<RecibosDomiciliados> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable("recibosDomiciliados")
    public Page<RecibosDomiciliados> list(Pageable pageable, Specification<RecibosDomiciliados> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
    
}
