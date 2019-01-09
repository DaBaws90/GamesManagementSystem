package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Competicion;
import com.example.AdrianCarrasco.model.CompeticionModel;

@Component("competicionConverter")
public class CompeticionConverter {

public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public CompeticionModel transform(Competicion competicion) {
		return dozer.map(competicion, CompeticionModel.class);
	}
	
	public Competicion transform(CompeticionModel competicionModel) {
		return dozer.map(competicionModel, Competicion.class);
	}
}
