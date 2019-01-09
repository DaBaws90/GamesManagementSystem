package com.example.AdrianCarrasco.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
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
	@Size(min=2, max=80)
	private String descripcion;
	
	@NotNull
	private Date lanzamiento;
	
	@NotNull
	@NotEmpty
//	Selección desde desplegable
	private String pegi;
	
	@NotNull
	@NotEmpty
//	Selección desde desplegable
	private String tipo;
	
	
	private String caratula;
	
	private boolean alquilado;
	
	private boolean comprado;
	
	@NotNull
	@Digits(fraction=2, integer=2) 
	private float precio;
//	@DecimalMin(value = "0.01", inclusive = true)
//	private BigDecimal precio;
	
	
	private Set<CategoriaModel> categoriasModel = new HashSet<>();
	
	private Set<PlataformaModel> plataformasModel = new HashSet<>();
	
	private Set<AlquilerModel> alquileresModel = new HashSet<>();
	
	private VentaModel ventaModel;

	private Set<CompeticionModel> competicionesModel = new HashSet<>();

	public JuegoModel() {
		super();
	}

	public JuegoModel(int id, String titulo, String descripcion, Date lanzamiento, String pegi, String tipo,
			String caratula, boolean alquilado, boolean comprado, float precio, Set<CategoriaModel> categoriasModel,
			Set<PlataformaModel> plataformasModel, Set<AlquilerModel> alquileresModel, VentaModel ventaModel,
			Set<CompeticionModel> competicionesModel) {
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
		this.categoriasModel = categoriasModel;
		this.plataformasModel = plataformasModel;
		this.alquileresModel = alquileresModel;
		this.ventaModel = ventaModel;
		this.competicionesModel = competicionesModel;
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

	public Set<CategoriaModel> getCategoriasModel() {
		return categoriasModel;
	}

	public void setCategoriasModel(Set<CategoriaModel> categoriasModel) {
		this.categoriasModel = categoriasModel;
	}

	public Set<PlataformaModel> getPlataformasModel() {
		return plataformasModel;
	}

	public void setPlataformasModel(Set<PlataformaModel> plataformasModel) {
		this.plataformasModel = plataformasModel;
	}

	public Set<AlquilerModel> getAlquileresModel() {
		return alquileresModel;
	}

	public void setAlquileresModel(Set<AlquilerModel> alquileresModel) {
		this.alquileresModel = alquileresModel;
	}

	public VentaModel getVentaModel() {
		return ventaModel;
	}

	public void setVentaModel(VentaModel ventaModel) {
		this.ventaModel = ventaModel;
	}

	public Set<CompeticionModel> getCompeticionesModel() {
		return competicionesModel;
	}

	public void setCompeticionesModel(Set<CompeticionModel> competicionesModel) {
		this.competicionesModel = competicionesModel;
	}

	@Override
	public String toString() {
		return "JuegoModel [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", lanzamiento="
				+ lanzamiento + ", pegi=" + pegi + ", tipo=" + tipo + ", caratula=" + caratula + ", alquilado="
				+ alquilado + ", comprado=" + comprado + ", precio=" + precio + ", ventaModel=" + ventaModel + "]";
	}
	

}
