package com.example.AdrianCarrasco.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class AlquilerModel {
	
	private int id;
	
//	@NotNull
	private Date fechaAlquiler;
	
	@NotNull
	private Date fechaDevolucion;
	
//	@NotNull
	private UserModel userModel;
	
	@NotNull
	private JuegoModel juegoModel;

	public AlquilerModel() {
		super();
	}

	public AlquilerModel(int id, Date fechaAlquiler, Date fechaDevolucion, UserModel user, JuegoModel juego) {
		super();
		this.id = id;
		this.fechaAlquiler = fechaAlquiler;
		this.fechaDevolucion = fechaDevolucion;
		this.userModel = user;
		this.juegoModel = juego;
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

	public UserModel getUser() {
		return userModel;
	}

	public void setUser(UserModel userModel) {
		this.userModel = userModel;
	}

	public JuegoModel getJuego() {
		return juegoModel;
	}

	public void setJuego(JuegoModel juegoModel) {
		this.juegoModel = juegoModel;
	}

	@Override
	public String toString() {
		return "AlquilerModel [id=" + id + ", fechaAlquiler=" + fechaAlquiler + ", fechaDevolucion=" + fechaDevolucion
				+ ", userModel=" + userModel + ", juegoModel=" + juegoModel + "]";
	}

	
}
