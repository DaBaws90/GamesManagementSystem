package com.example.AdrianCarrasco.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.entity.User;
import com.example.AdrianCarrasco.entity.Venta;

@Repository("ventaJpaRepository")
public interface VentaJpaRepository extends JpaRepository<Venta, Serializable>{
//	public abstract Page<Venta> findAll(Pageable pageable);
	public abstract Venta findById(int id);
	public abstract List<Venta> findAllByFecha(Date fecha);
	public abstract Venta findByJuego(Juego juego);
	public abstract List<Venta> findAllByUser(User user);
	
	public abstract Venta findByFactura(String factura);
}
