package com.example.AdrianCarrasco.service;

import java.util.List;

import com.example.AdrianCarrasco.entity.Noticia;
import com.example.AdrianCarrasco.model.NoticiaModel;

public interface NoticiaService {
	
	public abstract List<NoticiaModel> listAllNoticias();
	public abstract Noticia addNoticia(NoticiaModel noticiaModel);
	public abstract Noticia updateNoticia(NoticiaModel noticiaModel);
	public abstract boolean deleteNoticia(int id);
	public abstract NoticiaModel findById(int id);
//	public abstract Noticia findByName(NoticiaModel noticiaModel);

}
