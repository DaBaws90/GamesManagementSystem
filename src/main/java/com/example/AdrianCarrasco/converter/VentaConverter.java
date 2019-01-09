package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.Venta;
import com.example.AdrianCarrasco.model.VentaModel;

@Component("ventaConverter")
public class VentaConverter {

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public VentaModel transform(Venta venta) {
		return dozer.map(venta, VentaModel.class);
	}
	
	public Venta transform(VentaModel ventaModel) {
		return dozer.map(ventaModel, Venta.class);
	}
}
