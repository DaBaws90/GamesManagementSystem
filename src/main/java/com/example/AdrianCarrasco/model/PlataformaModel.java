package com.example.AdrianCarrasco.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlataformaModel {
	
	private int id;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=80)
	private String nombre;
	
	private Set<JuegoModel> juegosModels = new HashSet<JuegoModel>();

	public PlataformaModel() {
		super();
	}

	public PlataformaModel(int id, String nombre, Set<JuegoModel> juegosModels) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.juegosModels = juegosModels;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<JuegoModel> getJuegos() {
		return juegosModels;
	}

	public void setJuegos(Set<JuegoModel> juegosModels) {
		this.juegosModels = juegosModels;
	}

	@Override
	public String toString() {
		return "PlataformaModel [id=" + id + ", nombre=" + nombre + "]";
	}


}
