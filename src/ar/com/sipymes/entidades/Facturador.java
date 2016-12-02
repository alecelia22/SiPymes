package ar.com.sipymes.entidades;

import java.util.Date;

/**
 * Persona o Empresa que realiza la factura
 */
public class Facturador {

	private String nombreFantasia;
	private String razonSocial;
	private String domicilio;
	// Iva inscripto, etc
	private Byte condicionIva;
	private Long cuit;
	private Long ingresosBrutos;
	private Date fechaInicio;

	public String getNombreFantasia() {
		return nombreFantasia;
	}

	public void setNombreFantasia(String nombreFantasia) {
		this.nombreFantasia = nombreFantasia;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Byte getCondicionIva() {
		return condicionIva;
	}

	public void setCondicionIva(Byte condicionIva) {
		this.condicionIva = condicionIva;
	}

	public Long getCuit() {
		return cuit;
	}

	public void setCuit(Long cuit) {
		this.cuit = cuit;
	}

	public Long getIngresosBrutos() {
		return ingresosBrutos;
	}

	public void setIngresosBrutos(Long ingresosBrutos) {
		this.ingresosBrutos = ingresosBrutos;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
}
