package com.example.AdrianCarrasco.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ParticipacionModel {

	private int id;

	@NotNull
	private UserModel userModel;

	@NotNull
	private CompeticionModel competicionModel;

//	Puede ser null si todavía no se celebró la competición
	@Size(min=1)
	private Integer posicion;

	public ParticipacionModel() {
		super();
	}

	public ParticipacionModel(int id, UserModel userModel, CompeticionModel competicionModel, Integer posicion) {
		super();
		this.id = id;
		this.userModel = userModel;
		this.competicionModel = competicionModel;
		this.posicion = posicion;
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

	public void setUser(UserModel userModel) {
		this.userModel = userModel;
	}

	public CompeticionModel getCompeticion() {
		return competicionModel;
	}

	public void setCompeticion(CompeticionModel competicionModel) {
		this.competicionModel = competicionModel;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		return "ParticipacionModel [id=" + id + ", userModel=" + userModel + ", competicionModel=" + competicionModel
				+ ", posicion=" + posicion + "]";
	}

	
}