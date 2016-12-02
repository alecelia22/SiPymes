package ar.com.sipymes.action;

import java.util.Date;

import ar.com.sipymes.constantes.TipoComprobante;
import ar.com.sipymes.constantes.TipoDocumento;
import ar.com.sipymes.entidades.Autenticacion;
import ar.com.sipymes.entidades.Factura;
import ar.com.sipymes.entidades.Facturado;
import ar.com.sipymes.entidades.Facturador;
import ar.com.sipymes.entidades.Item;
import ar.com.sipymes.ws.AutenticarWS;

import com.opensymphony.xwork2.ActionSupport;

public class FacturarAction extends ActionSupport {

	private static final long serialVersionUID = -4271518833161261748L;

	/**
	 * 
	 */
	public String iniciarCargarFactura() {
		
		// Consigo el token y el sign
		Autenticacion autenticacion = null;
		
		
		try {
			AutenticarWS autenticarWS = new AutenticarWS();
			
			
			autenticacion = autenticarWS.obtenerWSAA();

			System.out.println("Token: " + autenticacion.getToken());
			System.out.println("Sign : " + autenticacion.getSign());
		} catch (Exception e) {
			System.out.println("ERROR======: " + e.getMessage());
		}

		// Cargo los datos de la factura
		Factura factura = new Factura();
		factura.setTipo(TipoComprobante.FACTURA_B);
		
		// Facturador
		Facturador facturador = new Facturador();
		facturador.setNombreFantasia("Ushuaia Extremo Travel");
		facturador.setRazonSocial("Estevez Gomez");
		facturador.setDomicilio("Ponton Rio Negro1021");
		//facturador.setCondicionIva();
		facturador.setCuit(20140103226L);
		facturador.setIngresosBrutos(9991208721L);
		facturador.setFechaInicio(new Date());
		factura.setFacturador(facturador);
		
		factura.setPuntoVenta(18);
		factura.setNumeroComprobante(6L);
		factura.setFechaEmision(new Date());
		
		Facturado facturado = new Facturado();
		facturado.setTipoDocumento(TipoDocumento.DNI);
		facturado.setNumeroDocumento(30815219L);
		//facturado.setCondicionIva();
		facturado.setCondicionVenta(new Integer(1).byteValue());
		facturado.setRazonSocial("Celia Alejandro");
		facturado.setDomicilio("San Martin");
		factura.setFacturado(facturado);
		
		Item item1 = new Item();
		item1.setCodigo(0L);
		item1.setDescripcion("Buceo");

		
		
		//"https://wswhomo.afip.gov.ar/wsfev1/service.asmx?WSDL";
		
		
		return SUCCESS;
	}
}
