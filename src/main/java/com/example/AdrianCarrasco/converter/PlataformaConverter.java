package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Plataforma;
import com.example.AdrianCarrasco.model.PlataformaModel;

@Component("plataformaConverter")
public class PlataformaConverter {

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public PlataformaModel transform(Plataforma plataforma) {
		return dozer.map(plataforma, PlataformaModel.class);
	}
	
	public Plataforma transform(PlataformaModel plataformaModel) {
		return dozer.map(plataformaModel, Plataforma.class);
	}
}
