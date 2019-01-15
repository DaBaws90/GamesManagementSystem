package com.example.AdrianCarrasco.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.converter.CompeticionConverter;
import com.example.AdrianCarrasco.converter.JuegoConverter;
import com.example.AdrianCarrasco.entity.Competicion;
import com.example.AdrianCarrasco.model.CompeticionModel;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.repository.CompeticionJpaRepository;
import com.example.AdrianCarrasco.service.CompeticionService;

@Service("competicionServiceImpl")
public class CompeticionServiceImpl implements CompeticionService{
	
	@Autowired
	@Qualifier("competicionJpaRepository")
	private CompeticionJpaRepository competicionJpaRepository;
	
	@Autowired
	@Qualifier("competicionConverter")
	private CompeticionConverter competicionConverter;
	
	@Autowired
	@Qualifier("juegoConverter")
	private JuegoConverter juegoConverter;
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;

	@Override
	public List<CompeticionModel> listAllCompeticiones() {
		return new ArrayList<CompeticionModel>(competicionJpaRepository.findAll().stream().map(c -> competicionConverter.transform(c)).collect(Collectors.toList()));
	}

	@Override
	public Competicion addCompeticion(CompeticionModel competicionModel) {
		return competicionJpaRepository.save(competicionConverter.transform(competicionModel));
	}

	@Override
	public Competicion updateCompeticion(CompeticionModel competicionModel) {
		return competicionJpaRepository.save(competicionConverter.transform(competicionModel));
	}

	@Override
	public boolean deleteCompeticion(int id) {
		if(competicionJpaRepository.findById(id) != null) {
			competicionJpaRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public CompeticionModel findById(int id) {
		return competicionConverter.transform(competicionJpaRepository.findById(id));
	}

	@Override
	public List<CompeticionModel> findAllByNombre(String nombre) {
		return new ArrayList<CompeticionModel>(competicionJpaRepository.findAllByNombre(nombre).stream().map(c -> competicionConverter.transform(c)).collect(Collectors.toList()));
	}

	@Override
	public List<CompeticionModel> findAllByLugar(String lugar) {
		return new ArrayList<CompeticionModel>(competicionJpaRepository.findAllByLugar(lugar).stream().map(c -> competicionConverter.transform(c)).collect(Collectors.toList()));
	}

	@Override
	public List<CompeticionModel> findAllByJuego(JuegoModel juegoModel) {
		return new ArrayList<CompeticionModel>(competicionJpaRepository.findAllByJuego(juegoConverter.transform(juegoModel)).stream().map(c -> competicionConverter.transform(c)).collect(Collectors.toList()));
	}

	@Override
	public List<CompeticionModel> findAllByFecha(Date fecha) {
		return new ArrayList<CompeticionModel>(competicionJpaRepository.findAllByFecha(fecha).stream().map(c -> competicionConverter.transform(c)).collect(Collectors.toList()));
	}

	@Override
	public boolean checkAvailability(CompeticionModel competicionModel) {
		for (Competicion competicion : competicionJpaRepository.findAll()) {
			if(competicion.getNombre().equals(competicionModel.getNombre()) && competicion.getDescripcion().equals(competicionModel.getDescripcion())
					&& competicion.getFecha().equals(competicionModel.getFecha()) && competicion.getLugar().equals(competicionModel.getLugar()) 
					&& competicion.getJuego().getId() == competicionModel.getJuego().getId()) {
				return false;
			}
		}
		return true;
	}
	
	
}
