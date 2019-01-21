package com.example.AdrianCarrasco.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class VentaModel {
	
	private int id;
	
//	@NotNull
	private UserModel userModel;
	
	@NotNull
	private JuegoModel juegoModel;
	
//	@NotNull
	private Date fecha;
	
	private String factura;

	public VentaModel() {
		super();
	}

	public VentaModel(int id, UserModel user, JuegoModel juego, Date fecha, String factura) {
		super();
		this.id = id;
		this.userModel = user;
		this.juegoModel = juego;
		this.fecha = fecha;
		this.factura = factura;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserModel getUser() {
		return userModel;
	}

	public void setUser(UserModel user) {
		this.userModel = user;
	}

	public JuegoModel getJuego() {
		return juegoModel;
	}

	public void setJuego(JuegoModel juego) {
		this.juegoModel = juego;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	@Override
	public String toString() {
		return "VentaModel [id=" + id + ", userModel=" + userModel + ", juegoModel=" + juegoModel + ", fecha=" + fecha
				+ ", factura#=" + factura + "]";
	}
	

}
