package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Alquiler;
import com.example.AdrianCarrasco.model.AlquilerModel;

@Component("alquilerConverter")
public class AlquilerConverter {

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public AlquilerModel transform(Alquiler alquiler) {
		return dozer.map(alquiler, AlquilerModel.class);
	}
	
	public Alquiler transform(AlquilerModel alquilerModel) {
		return dozer.map(alquilerModel, Alquiler.class);
	}
}
