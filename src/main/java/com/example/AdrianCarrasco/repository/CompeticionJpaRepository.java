package com.example.AdrianCarrasco.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Competicion;
import com.example.AdrianCarrasco.entity.Juego;

@Repository("competicionJpaRepository")
public interface CompeticionJpaRepository extends JpaRepository<Competicion, Serializable>{
	public abstract Competicion findById(int id);
	public abstract List<Competicion> findAllByNombre(String nombre);
	public abstract List<Competicion> findAllByLugar(String lugar);
	public abstract List<Competicion> findAllByJuego(Juego juego);
	public abstract List<Competicion> findAllByFecha(Date fecha);
}
