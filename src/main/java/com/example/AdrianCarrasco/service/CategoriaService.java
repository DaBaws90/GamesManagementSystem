package com.example.AdrianCarrasco.service;

import java.util.List;

import com.example.AdrianCarrasco.entity.Categoria;
import com.example.AdrianCarrasco.model.CategoriaModel;

public interface CategoriaService {
	public abstract List<CategoriaModel> listAllCategories();
	public abstract Categoria addCategory(CategoriaModel categoriaModel);
	public abstract Categoria updateCategory(CategoriaModel categoriaModel);
	public abstract boolean deleteCategory(int id);
	
	public abstract CategoriaModel findById(int id);
	public abstract CategoriaModel findByNombre(String nombre);
}
