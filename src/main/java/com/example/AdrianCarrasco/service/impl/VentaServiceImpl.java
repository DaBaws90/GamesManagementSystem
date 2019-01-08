package com.example.AdrianCarrasco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.entity.Venta;
import com.example.AdrianCarrasco.model.VentaModel;
import com.example.AdrianCarrasco.repository.VentaJpaRepository;
import com.example.AdrianCarrasco.service.VentaService;

@Service("ventaServiceImpl")
public class VentaServiceImpl implements VentaService{
	
	@Autowired
	@Qualifier("ventaJpaRepository")
	private VentaJpaRepository ventaJpaRepository;

	@Override
	public List<VentaModel> listAllVentas() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Venta addVenta(VentaModel ventaModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Venta updateVenta(VentaModel ventaModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteVenta(int id) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public Page<Venta> latestVentas() {
//		// TODO Auto-generated method stub
//		Pageable pageable = new PageRequest.of(0, 5, Sort.by("id"));
//		return null;
//	}

	
}
