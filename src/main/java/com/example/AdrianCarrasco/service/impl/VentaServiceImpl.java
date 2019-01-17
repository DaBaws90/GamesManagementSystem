package com.example.AdrianCarrasco.service.impl;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.converter.JuegoConverter;
import com.example.AdrianCarrasco.converter.UserConverter;
import com.example.AdrianCarrasco.converter.VentaConverter;
import com.example.AdrianCarrasco.entity.Venta;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.model.VentaModel;
import com.example.AdrianCarrasco.repository.VentaJpaRepository;
import com.example.AdrianCarrasco.service.VentaService;

@Service("ventaServiceImpl")
public class VentaServiceImpl implements VentaService{
	
	@Autowired
	@Qualifier("ventaJpaRepository")
	private VentaJpaRepository ventaJpaRepository;
	
	@Autowired
	@Qualifier("ventaConverter")
	private VentaConverter ventaConverter;
	
	@Autowired
	@Qualifier("userConverter")
	private UserConverter userConverter;
	
	@Autowired
	@Qualifier("juegoConverter")
	private JuegoConverter juegoConverter;

	@Override
	public List<VentaModel> listAllVentas() {
		return new ArrayList<VentaModel>(ventaJpaRepository.findAll().stream().map(v -> ventaConverter.transform(v)).collect(Collectors.toList()));
	}

	@Override
	public Venta addVenta(VentaModel ventaModel) {
		ventaModel.setFactura(String.valueOf(Instant.now().toEpochMilli()));
		return ventaJpaRepository.save(ventaConverter.transform(ventaModel));
	}

	@Override
	public Venta updateVenta(VentaModel ventaModel) {
		return ventaJpaRepository.save(ventaConverter.transform(ventaModel));
	}

	@Override
	public boolean deleteVenta(int id) {
		if(ventaJpaRepository.findById(id) != null) {
			ventaJpaRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public VentaModel findById(int id) {
		return ventaConverter.transform(ventaJpaRepository.findById(id));
	}

	@Override
	public List<VentaModel> findAllByFecha(Date fecha) {
		return new ArrayList<VentaModel>(ventaJpaRepository.findAllByFecha(fecha).stream().map(v -> ventaConverter.transform(v)).collect(Collectors.toList()));
	}

	@Override
	public VentaModel findByJuego(JuegoModel juegoModel) {
		if(ventaJpaRepository.findByJuego(juegoConverter.transform(juegoModel)) != null) {
			return ventaConverter.transform(ventaJpaRepository.findByJuego(juegoConverter.transform(juegoModel)));
		}
		else {
			return null;
		}
	}

	@Override
	public List<VentaModel> findAllByUser(UserModel userModel) {
		return new ArrayList<VentaModel>(ventaJpaRepository.findAllByUser(userConverter.transform(userModel)).stream().map(v -> ventaConverter.transform(v)).collect(Collectors.toList()));
	}

	@Override
	public VentaModel findByFactura(String factura) {
		return ventaConverter.transform(ventaJpaRepository.findByFactura(factura));
	}

	
}
