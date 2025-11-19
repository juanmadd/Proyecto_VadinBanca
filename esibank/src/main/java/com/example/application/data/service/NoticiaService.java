package com.example.application.data.service;

import com.example.application.data.entity.Cuenta;
import com.example.application.data.entity.Noticia;
import com.example.application.data.entity.User;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class NoticiaService {

    private final NoticiaRepository repository;

    public NoticiaService(NoticiaRepository repository) {
        this.repository = repository;
    }

    @Cacheable("noticia")
    public Optional<Noticia> get(Long id) {
        return repository.findById(id);
    }

    public Noticia update(Noticia entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Noticia> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable("noticia")
    public Page<Noticia> list(Pageable pageable, Specification<Noticia> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
    
    @Cacheable("noticia")
    public List<Noticia> getAllNoticias() {
        return repository.findAll();
    }

}
