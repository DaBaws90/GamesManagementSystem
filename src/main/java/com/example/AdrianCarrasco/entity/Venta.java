package com.example.AdrianCarrasco.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ventas")
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
	private User user;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "factura")
	private String factura;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idjuego")
//	@MapsId
	private Juego juego;

	public Venta() {
		super();
	}

	public Venta(int id, User user, Date fecha, String factura, Juego juego) {
		super();
		this.id = id;
		this.user = user;
		this.fecha = fecha;
		this.factura = factura;
		this.juego = juego;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", user=" + user + ", fecha=" + fecha + ", factura#=" + factura + ", juego=" + juego
				+ "]";
	}
	
	
}
