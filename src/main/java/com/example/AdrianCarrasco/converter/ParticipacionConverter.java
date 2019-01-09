package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Participacion;
import com.example.AdrianCarrasco.model.ParticipacionModel;

@Component("participacionConverter")
public class ParticipacionConverter {

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public ParticipacionModel transform(Participacion participacion) {
		return dozer.map(participacion, ParticipacionModel.class);
	}
	
	public Participacion transform(ParticipacionModel participacionModel) {
		return dozer.map(participacionModel, Participacion.class);
	}
}
