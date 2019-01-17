package com.example.AdrianCarrasco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "participaciones")
public class Participacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iduser")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idcompeticion")
	private Competicion competicion;
	
	@Column(name = "posicion")
	private int posicion;

	public Participacion() {
		super();
	}

	public Participacion(int id, User user, Competicion competicion, int posicion) {
		super();
		this.id = id;
		this.user = user;
		this.competicion = competicion;
		this.posicion = posicion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Competicion getCompeticion() {
		return competicion;
	}

	public void setCompeticion(Competicion competicion) {
		this.competicion = competicion;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		return "Participacion [id=" + id + ", user=" + user + ", competicion=" + competicion + ", posicion=" + posicion
				+ "]";
	}

	
}
