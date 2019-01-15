package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.entity.Participacion;
import com.example.AdrianCarrasco.model.ParticipacionModel;

@Component("participacionConverter")
public class ParticipacionConverter {
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;

	private DozerBeanMapper dozer = new DozerBeanMapper();
	
	public ParticipacionModel transform(Participacion participacion) {
		logger.regularMessage(participacion.toString());
		return dozer.map(participacion, ParticipacionModel.class);
	}
	
	public Participacion transform(ParticipacionModel participacionModel) {
		return dozer.map(participacionModel, Participacion.class);
	}
}
