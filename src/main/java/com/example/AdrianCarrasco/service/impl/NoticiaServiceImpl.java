package com.example.AdrianCarrasco.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.converter.NoticiaConverter;
import com.example.AdrianCarrasco.entity.Noticia;
import com.example.AdrianCarrasco.model.NoticiaModel;
import com.example.AdrianCarrasco.repository.NoticiaJpaRepository;
import com.example.AdrianCarrasco.service.NoticiaService;

@Service("noticiaServiceImpl")
public class NoticiaServiceImpl implements NoticiaService{

	@Autowired
	@Qualifier("noticiaJpaRepository")
	private NoticiaJpaRepository noticiaJpaRepository;
	
	@Autowired
	@Qualifier("noticiaConverter")
	private NoticiaConverter noticiaConverter;
	
	@Override
	public List<NoticiaModel> listAllNoticias() {
		return noticiaJpaRepository.findAll().stream().map(c -> noticiaConverter.entidadModelo(c)).collect(Collectors.toList());
	}

	@Override
	public Noticia addNoticia(NoticiaModel noticiaModel) {
		return noticiaJpaRepository.save(noticiaConverter.modeloEntidad(noticiaModel));
	}

	@Override
	public Noticia updateNoticia(NoticiaModel noticiaModel) {
		return noticiaJpaRepository.save(noticiaConverter.modeloEntidad(noticiaModel));
	}

	@Override
	public boolean deleteNoticia(int id) {
		if(noticiaJpaRepository.findById(id) != null) {
			noticiaJpaRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public NoticiaModel findById(int id) {
		return noticiaConverter.entidadModelo(noticiaJpaRepository.findById(id));
	}

	@Override
	public NoticiaModel findByTitulo(String titulo) {
		return noticiaConverter.entidadModelo(noticiaJpaRepository.findByTitulo(titulo));
	}
	
	
}
