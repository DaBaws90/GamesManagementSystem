package com.example.AdrianCarrasco.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Noticia;

@Repository("noticiaJpaRepository")
public interface NoticiaJpaRepository extends JpaRepository<Noticia, Serializable>{
	
	public abstract Noticia findByTitulo(String titulo);
	public abstract Noticia findById(int id);
	
}
