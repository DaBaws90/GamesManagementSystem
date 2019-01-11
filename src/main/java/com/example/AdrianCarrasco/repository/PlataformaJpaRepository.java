package com.example.AdrianCarrasco.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Plataforma;

@Repository("plataformaJpaRepository")
public interface PlataformaJpaRepository extends JpaRepository<Plataforma, Serializable>{
	public abstract Plataforma findById(int id);
	public abstract Plataforma findByNombre(String nombre);
}
