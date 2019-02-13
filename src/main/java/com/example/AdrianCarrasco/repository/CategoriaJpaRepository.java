package com.example.AdrianCarrasco.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Categoria;

@Repository("categoriaJpaRepository")
public interface CategoriaJpaRepository extends JpaRepository<Categoria, Serializable>{
	public abstract Categoria findById(int id);
	public abstract Categoria findByNombre(String nombre);
//	public abstract Collection<? extends Categoria> findAllById(List<Integer> categoriasIds);
}
