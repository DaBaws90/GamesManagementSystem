package com.example.AdrianCarrasco.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.converter.JuegoConverter;
import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.repository.JuegoJpaRepository;
import com.example.AdrianCarrasco.service.JuegoService;

@Service("juegoServiceImpl")
public class JuegoServiceImpl implements JuegoService{

	@Autowired
	@Qualifier("juegoJpaRepository")
	private JuegoJpaRepository juegoJpaRepository;
	
	@Autowired
	@Qualifier("juegoConverter")
	private JuegoConverter juegoConverter;
	
	@Override
	public List<JuegoModel> listAllJuegos() {
		return new ArrayList<JuegoModel>(juegoJpaRepository.findAll().stream().map(j -> juegoConverter.transform(j)).collect(Collectors.toList()));
	}

	@Override
	public Juego addJuego(JuegoModel juegoModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Juego updateJuego(JuegoModel juegoModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteJuego(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JuegoModel findById(int id) {
		return juegoConverter.transform(juegoJpaRepository.findById(id));
	}

	@Override
	public List<JuegoModel> findAllByTitulo(String titulo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JuegoModel> findAllByTipo(String tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JuegoModel> findAllByLanzamiento(Date fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JuegoModel> findAllByPegi(String pegi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JuegoModel> findAllByCompradoFalse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JuegoModel> findAllByAlquiladoFalse() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
