package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Juego;
import com.example.AdrianCarrasco.model.JuegoModel;

@Component("juegoConverter")
public class JuegoConverter {

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public JuegoModel transform(Juego juego) {
		return dozer.map(juego, JuegoModel.class);
	}
	
	public Juego transform(JuegoModel juegoModel) {
		return dozer.map(juegoModel, Juego.class);
	}
}
