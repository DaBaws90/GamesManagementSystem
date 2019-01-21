package com.example.AdrianCarrasco.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class JuegoModel {
	
	private int id;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=80)
	private String titulo;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=1000)
	private String descripcion;
	
	@NotNull
	private Date lanzamiento;
	
	@NotNull
	@NotEmpty
	private String pegi;
	
	@NotNull
	@NotEmpty
	private String tipo;
	
	private String caratula;
	
	private boolean alquilado;
	
	@NotNull
	@Digits(fraction=2, integer=3)
	@DecimalMin("0.01")
//	@Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Máximo de 2 decimales precedido de al menos un entero")
	private float precio;
	
	@Min(1)
	private int stock;
	
	private Set<CategoriaModel> categoriasModel = new HashSet<CategoriaModel>();
	
	private Set<PlataformaModel> plataformasModel = new HashSet<PlataformaModel>();
	
	private Set<AlquilerModel> alquileresModel = new HashSet<AlquilerModel>();
	
	private Set<VentaModel> ventasModel = new HashSet<VentaModel>();

	private Set<CompeticionModel> competiciones = new HashSet<CompeticionModel>();

	public JuegoModel() {
		super();
	}

	public JuegoModel(int id, String titulo, String descripcion, Date lanzamiento, String pegi, String tipo,
			String caratula, boolean alquilado, float precio, int stock, Set<CategoriaModel> categoriasModel,
			Set<PlataformaModel> plataformasModel, Set<AlquilerModel> alquileresModel, Set<VentaModel> ventasModel,
			Set<CompeticionModel> competiciones) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.lanzamiento = lanzamiento;
		this.pegi = pegi;
		this.tipo = tipo;
		this.caratula = caratula;
		this.alquilado = alquilado;
		this.precio = precio;
		this.stock = stock;
		this.categoriasModel = categoriasModel;
		this.plataformasModel = plataformasModel;
		this.alquileresModel = alquileresModel;
		this.ventasModel = ventasModel;
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

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Set<CategoriaModel> getCategorias() {
		return categoriasModel;
	}

	public void setCategorias(Set<CategoriaModel> categoriasModel) {
		this.categoriasModel = categoriasModel;
	}

	public Set<PlataformaModel> getPlataformas() {
		return plataformasModel;
	}

	public void setPlataformas(Set<PlataformaModel> plataformasModel) {
		this.plataformasModel = plataformasModel;
	}

	public Set<AlquilerModel> getAlquileres() {
		return alquileresModel;
	}

	public void setAlquileres(Set<AlquilerModel> alquileresModel) {
		this.alquileresModel = alquileresModel;
	}

	public Set<VentaModel> getVentas() {
		return ventasModel;
	}

	public void setVentas(Set<VentaModel> ventasModel) {
		this.ventasModel = ventasModel;
	}

	public Set<CompeticionModel> getCompeticiones() {
		return competiciones;
	}

	public void setCompeticiones(Set<CompeticionModel> competiciones) {
		this.competiciones = competiciones;
	}

	@Override
	public String toString() {
		return "JuegoModel [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", lanzamiento="
				+ lanzamiento + ", pegi=" + pegi + ", tipo=" + tipo + ", caratula=" + caratula + ", alquilado="
				+ alquilado + ", precio=" + precio + ", stock=" + stock
				+ ", categoriasModel=" + categoriasModel + ", plataformasModel=" + plataformasModel + "]";
	}
	

}
