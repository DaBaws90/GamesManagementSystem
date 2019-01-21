package com.example.AdrianCarrasco.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Alquiler;
import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.entity.User;

@Repository("alquilerJpaRepository")
public interface AlquilerJpaRepository extends JpaRepository<Alquiler, Serializable>{
	public abstract Alquiler findById(int id);
	public abstract List<Alquiler> findAllByFechaAlquiler(Date fecha);
	public abstract List<Alquiler> findAllByFechaDevolucion(Date fecha);
	public abstract List<Alquiler> findAllByJuego(Juego juego);
	public abstract List<Alquiler> findAllByUser(User user);
	
	public abstract List<Alquiler> findAllByJuegoAlquiladoTrue();
}
