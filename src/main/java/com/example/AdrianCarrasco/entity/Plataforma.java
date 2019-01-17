package com.example.AdrianCarrasco.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "plataformas")
public class Plataforma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;

	@ManyToMany(mappedBy = "plataformas")
	private Set<Juego> juegos = new HashSet<Juego>();

	public Plataforma() {
		super();
	}

	public Plataforma(int id, String nombre, Set<Juego> juegos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.juegos = juegos;
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

	public Set<Juego> getJuegos() {
		return juegos;
	}

	public void setJuegos(Set<Juego> juegos) {
		this.juegos = juegos;
	}

	@Override
	public String toString() {
		return "Plataforma [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
