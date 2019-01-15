package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.entity.Competicion;
import com.example.AdrianCarrasco.model.CompeticionModel;
import com.example.AdrianCarrasco.service.JuegoService;

@Component("competicionConverter")
public class CompeticionConverter {
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("juegoConverter")
	private JuegoConverter juegoConverter;
	
	@Autowired
	@Qualifier("juegoServiceImpl")
	private JuegoService juegoService;

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
//	public CompeticionModel transform(Competicion competicion) {
//		CompeticionModel temp = dozer.map(competicion, CompeticionModel.class);
//		temp.setJuegoModel(juegoConverter.transform(competicion.getJuego()));;
//		return temp;
//	}
//	
//	public Competicion transform(CompeticionModel competicionModel) {
//		Competicion temp = dozer.map(competicionModel, Competicion.class);
//		temp.setJuego(juegoConverter.transform(juegoService.findById(competicionModel.getJuegoModel().getId())));
//		return temp;
//	}
	
	public CompeticionModel transform(Competicion competicion) {
		return dozer.map(competicion, CompeticionModel.class);
	}
	
	public Competicion transform(CompeticionModel competicionModel) {
		return dozer.map(competicionModel, Competicion.class);
	}
}
