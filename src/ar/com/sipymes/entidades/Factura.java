package ar.com.sipymes.entidades;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Factura {

	// A, B, C
	private Byte tipo;
	// COD.6, otros
	private Byte codigo;
	// Persona o Empresa que realiza la factura
	private Facturador facturador;

	private Integer puntoVenta;
	private Long numeroComprobante;
	private Date fechaEmision;
	// Persona o Empresa a la que se le factura
	private Facturado facturado;
	// Items de la factura
	private List<Item> detalle;

	private BigDecimal subtotal;
	private BigDecimal otrosImpuestos;
	private BigDecimal total;
	private Long cae;
	private Date vencimientoCae;
	// Dato que hay q armar como codigo de barra
	private Long comprobanteAutorizado;

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	public Byte getCodigo() {
		return codigo;
	}

	public void setCodigo(Byte codigo) {
		this.codigo = codigo;
	}

	public Facturador getFacturador() {
		return facturador;
	}

	public void setFacturador(Facturador facturador) {
		this.facturador = facturador;
	}

	public Integer getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(Integer puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Long getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(Long numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Facturado getFacturado() {
		return facturado;
	}

	public void setFacturado(Facturado facturado) {
		this.facturado = facturado;
	}

	public List<Item> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<Item> detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getOtrosImpuestos() {
		return otrosImpuestos;
	}

	public void setOtrosImpuestos(BigDecimal otrosImpuestos) {
		this.otrosImpuestos = otrosImpuestos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getCae() {
		return cae;
	}

	public void setCae(Long cae) {
		this.cae = cae;
	}

	public Date getVencimientoCae() {
		return vencimientoCae;
	}

	public void setVencimientoCae(Date vencimientoCae) {
		this.vencimientoCae = vencimientoCae;
	}

	public Long getComprobanteAutorizado() {
		return comprobanteAutorizado;
	}

	public void setComprobanteAutorizado(Long comprobanteAutorizado) {
		this.comprobanteAutorizado = comprobanteAutorizado;
	}
}
