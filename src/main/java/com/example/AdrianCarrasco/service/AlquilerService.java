package com.example.AdrianCarrasco.service;

import java.sql.Date;
import java.util.List;

import com.example.AdrianCarrasco.entity.Alquiler;
import com.example.AdrianCarrasco.model.AlquilerModel;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.model.UserModel;

public interface AlquilerService {
	public abstract List<AlquilerModel> listAllAlquileres();
	public abstract Alquiler addAlquiler(AlquilerModel alquilerModel);
	public abstract Alquiler updateAlquiler(AlquilerModel alquilerModel);
	public abstract boolean deleteAlquiler(int id);
	
	public abstract AlquilerModel findById(int id);
	public abstract List<AlquilerModel> findAllByFechaAlquiler(Date fecha);
	public abstract List<AlquilerModel> findAllByFechaDevolucion(Date fecha);
	public abstract List<AlquilerModel> findAllByJuego(JuegoModel juegoModel);
	public abstract List<AlquilerModel> findAllByUser(UserModel userModel);
	
	public abstract boolean checkAvailability(JuegoModel juegoModel);
	public abstract List<AlquilerModel> findAllByJuegoAlquiladoTrue();
	
}
