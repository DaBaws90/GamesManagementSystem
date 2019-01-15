package com.example.AdrianCarrasco.service;

import java.sql.Date;
import java.util.List;

import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.model.JuegoModel;

public interface JuegoService {
	public abstract List<JuegoModel> listAllJuegos();
	public abstract Juego addJuego(JuegoModel juegoModel);
	public abstract Juego updateJuego(JuegoModel juegoModel);
	public abstract boolean deleteJuego(int id);
	
	public abstract JuegoModel findById(int id);
	
	public abstract List<JuegoModel> findAllByTitulo(String titulo);
	public abstract List<JuegoModel> findAllByTipo(String tipo);
	public abstract List<JuegoModel> findAllByLanzamiento(Date fecha);
	public abstract List<JuegoModel> findAllByPegi(String pegi);
	
	public abstract List<JuegoModel> findAllByCompradoFalse();
	public abstract List<JuegoModel> findAllByAlquiladoFalse();
}
