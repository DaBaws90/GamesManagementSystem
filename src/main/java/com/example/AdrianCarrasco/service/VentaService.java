package com.example.AdrianCarrasco.service;

import java.util.List;

import com.example.AdrianCarrasco.entity.Venta;
import com.example.AdrianCarrasco.model.VentaModel;

public interface VentaService {
	public abstract List<VentaModel> listAllVentas();
	public abstract Venta addVenta(VentaModel ventaModel);
	public abstract Venta updateVenta(VentaModel ventaModel);
	public abstract boolean deleteVenta(int id);
	
//	public abstract Page<Venta> latestVentas();
}
