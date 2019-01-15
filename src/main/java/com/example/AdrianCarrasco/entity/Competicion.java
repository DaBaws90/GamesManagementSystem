package com.example.AdrianCarrasco.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "competiciones")
public class Competicion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "lugar")
	private String lugar;

	@OneToMany(mappedBy="competicion", cascade = CascadeType.REMOVE)
	private Set<Participacion> participaciones = new HashSet<Participacion>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idjuego")
	private Juego juego;

	public Competicion() {
		super();
	}

	public Competicion(int id, String nombre, String descripcion, Date fecha, String lugar,
			Set<Participacion> participaciones, Juego juego) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.lugar = lugar;
		this.participaciones = participaciones;
		this.juego = juego;
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

	public Set<Participacion> getParticipaciones() {
		return participaciones;
	}

	public void setParticipaciones(Set<Participacion> participaciones) {
		this.participaciones = participaciones;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	@Override
	public String toString() {
		return "Competicion [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha
				+ ", lugar=" + lugar + ", juego=" + juego + "]";
	}


}
