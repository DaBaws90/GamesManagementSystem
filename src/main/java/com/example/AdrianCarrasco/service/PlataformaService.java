package com.example.AdrianCarrasco.service;

import java.util.List;

import com.example.AdrianCarrasco.entity.Plataforma;
import com.example.AdrianCarrasco.model.PlataformaModel;

public interface PlataformaService {
	public abstract List<PlataformaModel> listAllPlataformas();
	public abstract Plataforma addPlataforma(PlataformaModel plataformaModel);
	public abstract Plataforma updatePlataforma(PlataformaModel plataformaModel);
	public abstract boolean deletePlataforma(int id);
	
	public abstract PlataformaModel findById(int id);
	public abstract PlataformaModel findByNombre(String nombre);
}
