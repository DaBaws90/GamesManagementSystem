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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "juegos")
public class Juego {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "lanzamiento")
	private Date lanzamiento;
	
	@Column(name = "pegi")
	private String pegi;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "caratula")
	private String caratula;
	
	@Column(name = "alquilado")
	private boolean alquilado;
	
	@Column(name = "comprado")
	private boolean comprado;
	
	@Column(name = "precio")
	private float precio;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "juego_categoria",
			joinColumns = @JoinColumn(name = "idjuego"),
			inverseJoinColumns = @JoinColumn(name = "idcategoria"))
	private Set<Categoria> categorias = new HashSet<Categoria>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) 
	@JoinTable(name = "juego_plataforma",
			joinColumns = @JoinColumn(name = "idjuego"),
			inverseJoinColumns = @JoinColumn(name = "idplataforma"))
	private Set<Plataforma> plataformas = new HashSet<Plataforma>();
	
	@OneToMany(mappedBy = "juego", cascade = CascadeType.REMOVE)
	private Set<Alquiler> alquileres = new HashSet<Alquiler>();
	
	@OneToOne(mappedBy = "juego", cascade = CascadeType.REMOVE, // Podría ser ALL si fallase
			fetch = FetchType.EAGER)
	private Venta venta;

	@OneToMany(mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Competicion> competiciones = new HashSet<Competicion>();

	public Juego() {
		super();
	}

	public Juego(int id, String titulo, String descripcion, Date lanzamiento, String pegi, String tipo, String caratula,
			boolean alquilado, boolean comprado, float precio, Set<Categoria> categorias, Set<Plataforma> plataformas,
			Set<Alquiler> alquileres, Venta venta, Set<Competicion> competiciones) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.lanzamiento = lanzamiento;
		this.pegi = pegi;
		this.tipo = tipo;
		this.caratula = caratula;
		this.alquilado = alquilado;
		this.comprado = comprado;
		this.precio = precio;
		this.categorias = categorias;
		this.plataformas = plataformas;
		this.alquileres = alquileres;
		this.venta = venta;
		this.competiciones = competiciones;
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

	public Date getLanzamiento() {
		return lanzamiento;
	}

	public void setLanzamiento(Date lanzamiento) {
		this.lanzamiento = lanzamiento;
	}

	public String getPegi() {
		return pegi;
	}

	public void setPegi(String pegi) {
		this.pegi = pegi;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public boolean isAlquilado() {
		return alquilado;
	}

	public void setAlquilado(boolean alquilado) {
		this.alquilado = alquilado;
	}

	public boolean isComprado() {
		return comprado;
	}

	public void setComprado(boolean comprado) {
		this.comprado = comprado;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<Plataforma> getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(Set<Plataforma> plataformas) {
		this.plataformas = plataformas;
	}

	public Set<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(Set<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Set<Competicion> getCompeticiones() {
		return competiciones;
	}

	public void setCompeticiones(Set<Competicion> competiciones) {
		this.competiciones = competiciones;
	}

	@Override
	public String toString() {
		return "Juego [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", lanzamiento="
				+ lanzamiento + ", pegi=" + pegi + ", tipo=" + tipo + ", caratula=" + caratula + ", alquilado="
				+ alquilado + ", comprado=" + comprado + ", precio=" + precio + ", venta=" + venta + "]";
	}
	
	
}
