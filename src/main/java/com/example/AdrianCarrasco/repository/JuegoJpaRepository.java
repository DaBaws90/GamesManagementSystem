package com.example.AdrianCarrasco.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Juego;

@Repository("juegoJpaRepository")
public interface JuegoJpaRepository extends JpaRepository<Juego, Serializable>{
	public abstract Juego findById(int id);
	
	public abstract List<Juego> findAllByTitulo(String titulo);
	public abstract List<Juego> findAllByTipo(String tipo);
	public abstract List<Juego> findAllByLanzamiento(Date fecha);
	public abstract List<Juego> findAllByPegi(String pegi);
	
	public abstract List<Juego> findAllByAlquiladoFalse();
	
	public abstract List<Juego> findAllByStockGreaterThanAndTipo(int stock, String tipo);
	public abstract List<Juego> findAllByTipoAndAlquiladoFalse(String tipo);
}
