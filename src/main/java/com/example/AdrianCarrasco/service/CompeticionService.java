package com.example.AdrianCarrasco.service;

import java.sql.Date;
import java.util.List;

import com.example.AdrianCarrasco.entity.Competicion;
import com.example.AdrianCarrasco.model.CompeticionModel;
import com.example.AdrianCarrasco.model.JuegoModel;

public interface CompeticionService {
	public abstract List<CompeticionModel> listAllCompeticiones();
	public abstract Competicion addCompeticion(CompeticionModel competicionModel);
	public abstract Competicion updateCompeticion(CompeticionModel competicionModel);
	public abstract boolean deleteCompeticion(int id);
	
	public abstract CompeticionModel findById(int id);
	public abstract List<CompeticionModel> findAllByNombre(String nombre);
	public abstract List<CompeticionModel> findAllByLugar(String lugar);
	public abstract List<CompeticionModel> findAllByJuego(JuegoModel juegoModel);
	public abstract List<CompeticionModel> findAllByFecha(Date fecha);
	
	public abstract boolean checkAvailability(CompeticionModel competicionModel);
}
