package com.example.AdrianCarrasco.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class AlquilerModel {
	
	private int id;
	
	@NotNull
	private Date fechaAlquiler;
	
	@NotNull
	private Date fechaDevolucion;
	
	@NotNull
	private UserModel userModel;
	
	@NotNull
	private JuegoModel juegoModel;

	public AlquilerModel() {
		super();
	}

	public AlquilerModel(int id, Date fechaAlquiler, Date fechaDevolucion, UserModel userModel, JuegoModel juegoModel) {
		super();
		this.id = id;
		this.fechaAlquiler = fechaAlquiler;
		this.fechaDevolucion = fechaDevolucion;
		this.userModel = userModel;
		this.juegoModel = juegoModel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaAlquiler() {
		return fechaAlquiler;
	}

	public void setFechaAlquiler(Date fechaAlquiler) {
		this.fechaAlquiler = fechaAlquiler;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
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

	@Override
	public String toString() {
		return "AlquilerModel [id=" + id + ", fechaAlquiler=" + fechaAlquiler + ", fechaDevolucion=" + fechaDevolucion
				+ ", userModel=" + userModel.getUsername() + ", juegoModel=" + juegoModel.getTitulo() + "]";
	}

	
}
