package com.example.AdrianCarrasco.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.converter.CategoriaConverter;
import com.example.AdrianCarrasco.converter.JuegoConverter;
import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.model.CategoriaModel;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.repository.CategoriaJpaRepository;
import com.example.AdrianCarrasco.repository.JuegoJpaRepository;
import com.example.AdrianCarrasco.service.CategoriaService;
import com.example.AdrianCarrasco.service.JuegoService;
import com.example.AdrianCarrasco.service.PlataformaService;

@Service("juegoServiceImpl")
public class JuegoServiceImpl implements JuegoService{

	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("juegoJpaRepository")
	private JuegoJpaRepository juegoJpaRepository;
	
	@Autowired
	@Qualifier("juegoConverter")
	private JuegoConverter juegoConverter;
	
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Autowired
	@Qualifier("categoriaConverter")
	private CategoriaConverter categoriaConverter;
	
	@Autowired
	@Qualifier("categoriaJpaRepository")
	private CategoriaJpaRepository categoriaJpaRepository;
	
	@Autowired
	@Qualifier("plataformaServiceImpl")
	private PlataformaService plataformaService;
	
	@Override
	public List<JuegoModel> listAllJuegos() {
		return new ArrayList<JuegoModel>(juegoJpaRepository.findAll().stream().map(j -> juegoConverter.transform(j)).collect(Collectors.toList()));
	}

	@Override
	public Juego addJuego(JuegoModel juegoModel, List<Integer> categoriasIds, List<Integer> plataformasIds) {
		Set<CategoriaModel> categoriasSelected = new HashSet<CategoriaModel>();
		
		
//		List<CategoriaModel> categoriasSelected = new ArrayList<CategoriaModel>();
		for (Integer n : categoriasIds) {
			categoriasSelected.add(categoriaService.findById(n));
		}
//		for (CategoriaModel categoriaModel : categoriasSelected) {
//			
//		}
//		Set<CategoriaModel> categoriasSaved = new HashSet<CategoriaModel>(categoriasSelected);
		juegoModel.setAlquilado(false);
		juegoModel.setCategorias(categoriasSelected);
//		Juego juego = juegoJpaRepository.save(juegoConverter.transform(juegoModel));
//		juego.setCategorias(categoriasSaved.stream().map(c -> categoriaConverter.transform(c)).collect(Collectors.toSet()));
//		logger.regularMessage(juego.toString());
//		return juegoJpaRepository.save(juego);
		
		return juegoJpaRepository.save(juegoConverter.transform(juegoModel));
	}

	@Override
	public Juego updateJuego(JuegoModel juegoModel) {
		return juegoJpaRepository.save(juegoConverter.transform(juegoModel));
	}

	@Override
	public boolean deleteJuego(int id) {
		if(juegoJpaRepository.findById(id) != null) {
			juegoJpaRepository.deleteById(id);
			return true;
		}
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
		return new ArrayList<JuegoModel>(juegoJpaRepository.findAllByTipo(tipo).stream()
				.map(j -> juegoConverter.transform(j)).collect(Collectors.toList()));
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
	public List<JuegoModel> findAllByAlquiladoFalse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JuegoModel> findAllByStockGreaterThanAndTipo(int stock, String tipo) {
		return new ArrayList<JuegoModel>(juegoJpaRepository.findAllByStockGreaterThanAndTipo(stock, tipo).stream()
				.map(j -> juegoConverter.transform(j)).collect(Collectors.toList()));
	}

	@Override
	public List<JuegoModel> findAllByTipoAndAlquiladoFalse(String tipo) {
		return new ArrayList<JuegoModel>(juegoJpaRepository.findAllByTipoAndAlquiladoFalse(tipo).stream()
				.map(j -> juegoConverter.transform(j)).collect(Collectors.toList()));
	}

	@Override
	public boolean checkCoincidences(JuegoModel juegoModel) {
		for (Juego juego : juegoJpaRepository.findAll()) {
			if(juego.getTitulo().equals(juegoModel.getTitulo()) && juego.getTipo().equals(juegoModel.getTipo())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Slice<Juego> pageableJuegos() {
		// TODO Auto-generated method stub
		Pageable firstPageWithTwoElements = PageRequest.of(0, 2, Sort.by("id").descending());
		Slice<Juego> pageableJuegos = juegoJpaRepository.findAll(firstPageWithTwoElements);
		return pageableJuegos;
	}
	
	
	
}
