package ar.com.sipymes.entidades;

/**
 * Persona o Empresa a la que se le factura.
 */
public class Facturado {

	// DNI, CUIT, etc
	private Byte tipoDocumento;
	private Long numeroDocumento;
	private Byte condicionIva;
	// TC, TD, contado, cheque
	private Byte condicionVenta;
	private String razonSocial;
	private String domicilio;

	public Byte getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Byte tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Byte getCondicionIva() {
		return condicionIva;
	}

	public void setCondicionIva(Byte condicionIva) {
		this.condicionIva = condicionIva;
	}

	public Byte getCondicionVenta() {
		return condicionVenta;
	}

	public void setCondicionVenta(Byte condicionVenta) {
		this.condicionVenta = condicionVenta;
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
}
