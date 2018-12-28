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
@Table(name = "categorias")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToMany(mappedBy = "categorias")
	private Set<Juego> juegos = new HashSet<>();

	public Categoria() {
		super();
	}

	public Categoria(int id, String nombre, Set<Juego> juegos) {
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
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}