package com.example.AdrianCarrasco.service;

import java.sql.Date;
import java.util.List;

import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.entity.User;
import com.example.AdrianCarrasco.entity.Venta;
import com.example.AdrianCarrasco.model.VentaModel;

public interface VentaService {
	public abstract List<VentaModel> listAllVentas();
	public abstract Venta addVenta(VentaModel ventaModel);
	public abstract Venta updateVenta(VentaModel ventaModel);
	public abstract boolean deleteVenta(int id);
	
	public abstract VentaModel findById(int id);
	public abstract List<VentaModel> findAllByFecha(Date fecha);
	public abstract VentaModel findByJuego(Juego juego);
	public abstract List<VentaModel> findAllByUser(User user);
}
