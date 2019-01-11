package com.example.AdrianCarrasco.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoriaModel {

	private int id;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=80)
	private String nombre;
	
	private Set<JuegoModel> juegosModel = new HashSet<>();

	public CategoriaModel() {
		super();
	}

	public CategoriaModel(int id, String nombre, Set<JuegoModel> juegosModel) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.juegosModel = juegosModel;
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

	public Set<JuegoModel> getJuegosModel() {
		return juegosModel;
	}

	public void setJuegosModel(Set<JuegoModel> juegosModel) {
		this.juegosModel = juegosModel;
	}

	@Override
	public String toString() {
		return "CategoriaModel [id=" + id + ", nombre=" + nombre + "]";
	}


}