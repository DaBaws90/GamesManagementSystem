package com.example.AdrianCarrasco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.converter.CategoriaConverter;
import com.example.AdrianCarrasco.entity.Categoria;
import com.example.AdrianCarrasco.model.CategoriaModel;
import com.example.AdrianCarrasco.repository.CategoriaJpaRepository;
import com.example.AdrianCarrasco.service.CategoriaService;

@Service("categoriaServiceImpl")
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	@Qualifier("categoriaJpaRepository")
	private CategoriaJpaRepository categoriaJpaRepository;
	
	@Autowired
	@Qualifier("categoriaConverter")
	private CategoriaConverter categoriaConverter;

	@Override
	public List<CategoriaModel> listAllCategories() {
		return new ArrayList<CategoriaModel>(categoriaJpaRepository.findAll().stream().map(c -> categoriaConverter.transform(c)).collect(Collectors.toList()));
	}

	@Override
	public Categoria addCategory(CategoriaModel categoriaModel) {
		return categoriaJpaRepository.save(categoriaConverter.transform(categoriaModel));
	}

	@Override
	public Categoria updateCategory(CategoriaModel categoriaModel) {
		return categoriaJpaRepository.save(categoriaConverter.transform(categoriaModel));
	}

	@Override
	public boolean deleteCategory(int id) {
		if(categoriaJpaRepository.findById(id) != null) {
			categoriaJpaRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public CategoriaModel findById(int id) {
		return categoriaConverter.transform(categoriaJpaRepository.findById(id));
	}

	@Override
	public CategoriaModel findByNombre(String nombre) {
		if(categoriaJpaRepository.findByNombre(nombre) != null) {
			return categoriaConverter.transform(categoriaJpaRepository.findByNombre(nombre));
		}
		else {
			return null;
		}
	}
	
	
}
