package com.example.AdrianCarrasco.service.impl;

import java.io.IOException;
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
import com.example.AdrianCarrasco.converter.JuegoConverter;
import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.model.CategoriaModel;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.model.PlataformaModel;
import com.example.AdrianCarrasco.repository.JuegoJpaRepository;
import com.example.AdrianCarrasco.service.CategoriaService;
import com.example.AdrianCarrasco.service.FileService;
import com.example.AdrianCarrasco.service.JuegoService;
import com.example.AdrianCarrasco.service.PlataformaService;

@Service("juegoServiceImpl")
public class JuegoServiceImpl implements JuegoService{

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
	@Qualifier("plataformaServiceImpl")
	private PlataformaService plataformaService;
	
	@Autowired
	@Qualifier("fileServiceImpl")
	private FileService fileService;
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Override
	public List<JuegoModel> listAllJuegos() {
		return new ArrayList<JuegoModel>(juegoJpaRepository.findAll().stream().map(j -> juegoConverter.transform(j)).collect(Collectors.toList()));
	}

	@Override
	public Juego addJuego(JuegoModel juegoModel, List<Integer> categoriasIds, List<Integer> plataformasIds) {
		if(!categoriasIds.isEmpty()) {
			Set<CategoriaModel> categoriasSelected = new HashSet<CategoriaModel>();
			for (Integer n : categoriasIds) {
				categoriasSelected.add(categoriaService.findById(n));
			}
			juegoModel.setCategorias(categoriasSelected);
		}
		if(!plataformasIds.isEmpty()) {
			Set<PlataformaModel> plataformasSelected = new HashSet<PlataformaModel>();
			for (Integer n : plataformasIds) {
				plataformasSelected.add(plataformaService.findById(n));
			}
			juegoModel.setPlataformas(plataformasSelected);
		}
		return juegoJpaRepository.save(juegoConverter.transform(juegoModel));
	}

	@Override
	public Juego updateJuego(JuegoModel juegoModel, List<Integer> categoriasIds, List<Integer> plataformasIds) {
//		Si la lista de categorías viene vacía porque no se cambiaron las categorías al editar, que establezca las categorías que tenía, ya que el modelo
//		editado no tiene valores almacenados. Lo mismo se aplica a las plataformas. En caso de haber editado categorías o plataformas, se estableceran las
//		elecciones pertinentes tras haber recuperado todos los elementos.
		if(categoriasIds != null) {
			Set<CategoriaModel> categoriasSelected = new HashSet<CategoriaModel>();
			for (Integer n : categoriasIds) {
				categoriasSelected.add(categoriaService.findById(n));
			}
			juegoModel.setCategorias(categoriasSelected);
		}
		else {
			juegoModel.setCategorias(juegoConverter.transform(juegoJpaRepository.findById(juegoModel.getId())).getCategorias());
		}
		if(plataformasIds != null) {
			Set<PlataformaModel> plataformasSelected = new HashSet<PlataformaModel>();
			for (Integer n : plataformasIds) {
				plataformasSelected.add(plataformaService.findById(n));
			}
			juegoModel.setPlataformas(plataformasSelected);
		}
		else {
			juegoModel.setPlataformas(juegoConverter.transform(juegoJpaRepository.findById(juegoModel.getId())).getPlataformas());
		}
		return juegoJpaRepository.save(juegoConverter.transform(juegoModel));
	}

	@Override
	public boolean deleteJuego(int id) {
		Juego juego = juegoJpaRepository.findById(id);
		if(juego != null) {
			if(juego.getCaratula() != null) {
				try {
					fileService.deleteFile(juego.getCaratula());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			juegoJpaRepository.delete(juego);;
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
		Pageable firstPageWithTwoElements = PageRequest.of(0, 2, Sort.by("id").descending());
		Slice<Juego> pageableJuegos = juegoJpaRepository.findAll(firstPageWithTwoElements);
		return pageableJuegos;
	}

	@Override
	public Juego devolverJuego(JuegoModel juegoModel) {
		juegoModel.setAlquilado(false);
		juegoModel.setStock(juegoModel.getStock() + 1);
		return juegoJpaRepository.save(juegoConverter.transform(juegoModel));
	}
	
	
}
