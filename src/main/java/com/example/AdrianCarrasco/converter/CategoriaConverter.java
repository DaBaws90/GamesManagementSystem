package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Categoria;
import com.example.AdrianCarrasco.model.CategoriaModel;

@Component("categoriaConverter")
public class CategoriaConverter {
	
	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public CategoriaModel transform(Categoria categoria) {
		return dozer.map(categoria, CategoriaModel.class);
	}
	
	public Categoria transform(CategoriaModel categoriaModel) {
		return dozer.map(categoriaModel, Categoria.class);
	}
}
