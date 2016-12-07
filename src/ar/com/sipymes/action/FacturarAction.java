package ar.com.sipymes.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.sipymes.constantes.TipoComprobante;
import ar.com.sipymes.constantes.TipoConcepto;
import ar.com.sipymes.constantes.TipoDocumento;
import ar.com.sipymes.entidades.Autenticacion;
import ar.com.sipymes.entidades.Factura;
import ar.com.sipymes.entidades.Facturado;
import ar.com.sipymes.entidades.Facturador;
import ar.com.sipymes.entidades.Item;
import ar.com.sipymes.ws.AfipWS;
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
		item1.setTipoItem(TipoConcepto.SERVICIOS);
		item1.setCantidad(BigDecimal.ONE);
		item1.setUnidadMedida(new Byte("1"));
		item1.setPrecioUnitario(new BigDecimal(2500.00));
		item1.setBonficacion(BigDecimal.ZERO);
		item1.setImporteBonificacion(BigDecimal.ZERO);
		item1.setSubTotal(new BigDecimal(2500.00));		
		List<Item> items = new ArrayList<Item>();
		items.add(item1);
		factura.setDetalle(items);
		
		factura.setSubtotal(new BigDecimal(2500.00));
		factura.setOtrosImpuestos(BigDecimal.ZERO);
		factura.setTotal(new BigDecimal(2500.00));
		
		
		AfipWS afip = new AfipWS();
		afip.obtenerCAE(factura, autenticacion);
		
		
		//
		
		
		return SUCCESS;
	}
}
