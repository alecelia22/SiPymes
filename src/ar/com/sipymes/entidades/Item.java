package ar.com.sipymes.entidades;

import java.math.BigDecimal;

public class Item {

	private Byte tipoItem;
	private Long codigo;
	private String descripcion;
	private BigDecimal cantidad;
	// Unidad, kilo, etc
	private Byte unidadMedida;
	private BigDecimal precioUnitario;
	private BigDecimal bonficacion;
	private BigDecimal importeBonificacion;
	private BigDecimal subTotal;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public Byte getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(Byte unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getBonficacion() {
		return bonficacion;
	}

	public void setBonficacion(BigDecimal bonficacion) {
		this.bonficacion = bonficacion;
	}

	public BigDecimal getImporteBonificacion() {
		return importeBonificacion;
	}

	public void setImporteBonificacion(BigDecimal importeBonificacion) {
		this.importeBonificacion = importeBonificacion;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
}
