package com.example.AdrianCarrasco.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoticiaModel {
	
	private int id;
	
	@NotNull
	@NotEmpty
	@Size(min=10, max=80)
	private String titulo;
	
	@NotNull
	@NotEmpty
	@Size(min=80, max=200)
	private String descripcion;
	
	@NotNull
	@NotEmpty
	@Size(min=250, max=1000)
	private String contenido;

	public NoticiaModel() {
		super();
	}

	public NoticiaModel(int id, String titulo, String descripcion, String contenido) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Override
	public String toString() {
		return "NoticiaModel [id=" + id + ", titulo=" + titulo + "]";
	}
	

}
