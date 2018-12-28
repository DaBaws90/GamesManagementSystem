package com.example.AdrianCarrasco.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class VentaModel {
	
	private int id;
	
	@NotNull
	private UserModel userModel;
	
	@NotNull
	private JuegoModel juegoModel;
	
	@NotNull
	private Date fecha;

	public VentaModel() {
		super();
	}

	public VentaModel(int id, UserModel userModel, JuegoModel juegoModel, Date fecha) {
		super();
		this.id = id;
		this.userModel = userModel;
		this.juegoModel = juegoModel;
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public JuegoModel getJuegoModel() {
		return juegoModel;
	}

	public void setJuegoModel(JuegoModel juegoModel) {
		this.juegoModel = juegoModel;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "VentaModel [id=" + id + ", userModel=" + userModel.getUsername() + ", juegoModel=" + juegoModel.getTitulo() + ", fecha=" + fecha
				+ "]";
	}
	

}
