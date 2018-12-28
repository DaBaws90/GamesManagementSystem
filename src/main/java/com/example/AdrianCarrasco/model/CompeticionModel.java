package com.example.AdrianCarrasco.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompeticionModel {

	private int id;
	
	@NotNull
	@NotEmpty
	@Size(min=3, max=80)
	private String nombre;
	
	@NotNull
	@NotEmpty
	@Size(min=20, max=150)
	private String descripcion;
	
	@NotNull
	private Date fecha;
	
	@NotNull
	@NotEmpty
	@Size(min=3, max=80)
	private String lugar;
	
	private Set<ParticipacionModel> participacionesModel = new HashSet<>();
	
	@NotNull
	private JuegoModel juegoModel;

	public CompeticionModel() {
		super();
	}

	public CompeticionModel(int id, String nombre, String descripcion, Date fecha, String lugar, 
			Set<ParticipacionModel> participacionesModel, JuegoModel juegoModel) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.lugar = lugar;
		this.participacionesModel = participacionesModel;
		this.juegoModel = juegoModel;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Set<ParticipacionModel> getParticipacionesModel() {
		return participacionesModel;
	}

	public void setParticipacionesModel(Set<ParticipacionModel> participacionesModel) {
		this.participacionesModel = participacionesModel;
	}

	public JuegoModel getJuegoModel() {
		return juegoModel;
	}

	public void setJuegoModel(JuegoModel juegoModel) {
		this.juegoModel = juegoModel;
	}

	@Override
	public String toString() {
		return "CompeticionModel [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha
				+ ", lugar=" + lugar + ", juegoModel=" + juegoModel.getTitulo() + "]";
	}

	
}