package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Noticia;
import com.example.AdrianCarrasco.model.NoticiaModel;

@Component("noticiaConverter")
public class NoticiaConverter {

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public NoticiaModel entidadModelo(Noticia noticia) {
		return dozer.map(noticia, NoticiaModel.class);
	}
	
	public Noticia modeloEntidad(NoticiaModel noticiaModel) {
		return dozer.map(noticiaModel, Noticia.class);
	}
}
