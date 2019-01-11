package com.example.AdrianCarrasco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.converter.PlataformaConverter;
import com.example.AdrianCarrasco.entity.Plataforma;
import com.example.AdrianCarrasco.model.PlataformaModel;
import com.example.AdrianCarrasco.repository.PlataformaJpaRepository;
import com.example.AdrianCarrasco.service.PlataformaService;

@Service("plataformaServiceImpl")
public class PlataformaServiceImpl implements PlataformaService{

	@Autowired
	@Qualifier("plataformaJpaRepository")
	private PlataformaJpaRepository plataformaJpaRepository;
	
	@Autowired
	@Qualifier("plataformaConverter")
	private PlataformaConverter plataformaConverter;

	@Override
	public List<PlataformaModel> listAllPlataformas() {
		return new ArrayList<PlataformaModel>(plataformaJpaRepository.findAll().stream().map(p -> plataformaConverter.transform(p)).collect(Collectors.toList()));
	}

	@Override
	public Plataforma addPlataforma(PlataformaModel plataformaModel) {
		return plataformaJpaRepository.save(plataformaConverter.transform(plataformaModel));
	}

	@Override
	public Plataforma updatePlataforma(PlataformaModel plataformaModel) {
		return plataformaJpaRepository.save(plataformaConverter.transform(plataformaModel));
	}

	@Override
	public boolean deletePlataforma(int id) {
		if(plataformaJpaRepository.findById(id) != null) {
			plataformaJpaRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public PlataformaModel findById(int id) {
		return plataformaConverter.transform(plataformaJpaRepository.findById(id));
	}

	@Override
	public PlataformaModel findByNombre(String nombre) {
		if(plataformaJpaRepository.findByNombre(nombre) != null) {
			return plataformaConverter.transform(plataformaJpaRepository.findByNombre(nombre));
		}
		else {
			return null;
		}
	}
	
	
}
