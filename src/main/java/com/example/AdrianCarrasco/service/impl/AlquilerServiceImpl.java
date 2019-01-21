package com.example.AdrianCarrasco.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.converter.AlquilerConverter;
import com.example.AdrianCarrasco.converter.JuegoConverter;
import com.example.AdrianCarrasco.converter.UserConverter;
import com.example.AdrianCarrasco.entity.Alquiler;
import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.model.AlquilerModel;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.repository.AlquilerJpaRepository;
import com.example.AdrianCarrasco.repository.JuegoJpaRepository;
import com.example.AdrianCarrasco.service.AlquilerService;
import com.example.AdrianCarrasco.service.JuegoService;

@Service("alquilerServiceImpl")
public class AlquilerServiceImpl implements AlquilerService{
	
	@Autowired
	@Qualifier("alquilerJpaRepository")
	private AlquilerJpaRepository alquilerJpaRepository;
	
	@Autowired
	@Qualifier("alquilerConverter")
	private AlquilerConverter alquilerConverter;
	
	@Autowired
	@Qualifier("userConverter")
	private UserConverter userConverter;
	
	@Autowired
	@Qualifier("juegoConverter")
	private JuegoConverter juegoConverter;
	
	@Autowired
	@Qualifier("juegoServiceImpl")
	private JuegoService juegoService;
	
	@Autowired
	@Qualifier("juegoJpaRepository")
	private JuegoJpaRepository juegoJpaRepository;
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;

	@Override
	public List<AlquilerModel> listAllAlquileres() {
		return new ArrayList<AlquilerModel>(alquilerJpaRepository.findAll().stream()
				.map(a -> alquilerConverter.transform(a)).collect(Collectors.toList()));
	}

	@Override
	public Alquiler addAlquiler(AlquilerModel alquilerModel) {
		JuegoModel juegoModel = juegoService.findById(alquilerModel.getJuego().getId());
		juegoModel.setStock(juegoModel.getStock() - 1);
		juegoModel.setAlquilado(true);
		juegoJpaRepository.save(juegoConverter.transform(juegoModel));
		Date today = new Date(System.currentTimeMillis());
//		logger.regularMessage("TODAY IS: " + today);
		alquilerModel.setJuego(juegoModel);
		alquilerModel.setFechaAlquiler(today);
		return alquilerJpaRepository.save(alquilerConverter.transform(alquilerModel));
	}
	
	@Override
	public boolean checkAvailability(JuegoModel juegoModel) {
		return !juegoModel.isAlquilado();
	}

	@Override
	public Alquiler updateAlquiler(AlquilerModel alquilerModel) {
		// TODO Auto-generated method stub
//		Manejar juegoJpaRepository
		return null;
	}

	@Override
	public boolean deleteAlquiler(int id) {
		if(alquilerJpaRepository.findById(id) != null) {
			alquilerJpaRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public AlquilerModel findById(int id) {
		return alquilerConverter.transform(alquilerJpaRepository.findById(id));
	}

	@Override
	public List<AlquilerModel> findAllByFechaAlquiler(Date fecha) {
		return new  ArrayList<AlquilerModel>(alquilerJpaRepository.findAllByFechaAlquiler(fecha).stream()
				.map(a -> alquilerConverter.transform(a)).collect(Collectors.toList()));
	}

	@Override
	public List<AlquilerModel> findAllByFechaDevolucion(Date fecha) {
		return new ArrayList<AlquilerModel>(alquilerJpaRepository.findAllByFechaDevolucion(fecha).stream()
				.map(a -> alquilerConverter.transform(a)).collect(Collectors.toList()));
	}

	@Override
	public List<AlquilerModel> findAllByJuego(JuegoModel juegoModel) {
		return new ArrayList<AlquilerModel>(alquilerJpaRepository.findAllByJuego(juegoConverter.transform(juegoModel)).stream()
				.map(a -> alquilerConverter.transform(a)).collect(Collectors.toList()));
	}

	@Override
	public List<AlquilerModel> findAllByUser(UserModel userModel) {
		return new ArrayList<AlquilerModel>(alquilerJpaRepository.findAllByUser(userConverter.transform(userModel)).stream()
				.map(a -> alquilerConverter.transform(a)).collect(Collectors.toList()));
	}
	
	@Override
	public List<AlquilerModel> findAllByJuegoAlquiladoTrue(){
		List<Juego> juegosAlquilados = juegoJpaRepository.findAllByAlquiladoTrue();
		List<Alquiler> pendientes = new ArrayList<Alquiler>();
		for (Juego j : juegosAlquilados) {
			pendientes.add(alquilerJpaRepository.findAllByJuego(j).get(alquilerJpaRepository.findAllByJuego(j).size() - 1));
		}
		return new ArrayList<AlquilerModel>(pendientes.stream().map(a -> alquilerConverter.transform(a)).collect(Collectors.toList()));
	}

	
}
