package ar.com.sipymes.ws;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.NodeList;

import ar.com.sipymes.entidades.Autenticacion;
import ar.com.sipymes.entidades.Factura;

public class AfipWS {

	
	public void obtenerCAE(Factura factura, Autenticacion autenticacion) {
		// WSDL al cual acceder
//		String endpoint = "https://wswhomo.afip.gov.ar/wsfev1/service.asmx?WSDL";
		// DN de destino		
//		String dstDN = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239";

		
		try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // WSDL a la cual llamar
            String url = "http://wswhomo.afip.gov.ar/wsfev1/service.asmx";
            // Mensaje a enviar
            SOAPMessage mensaje = this.createSOAPRequest(autenticacion);
            
            /*
    		// Configuracion del Proxy, en caso de ser necesaria
    		System.setProperty("http.proxyHost", "proxylatam.indra.es");
    		System.setProperty("http.proxyPort", "8080");
    		System.setProperty("http.proxyUser", "ajcelia");
    		System.setProperty("http.proxyPassword", "Bichos18+");
    		*/
            
            // Hago la llamada
            SOAPMessage soapResponse = soapConnection.call(mensaje, url);

            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
		
	}
	
	private SOAPMessage createSOAPRequest(Autenticacion autenticacion) throws Exception {
		// Creo el factory con la version de SOAP que necesito
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Cargo el URI del xml
        SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
        envelope.addNamespaceDeclaration("ar", "http://ar.gov.afip.dif.FEV1/");

        /*
        <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ar="http://ar.gov.afip.dif.FEV1/">
		   <soap:Header/>
		   <soap:Body>
		      <ar:FECAEASolicitar>
		         <ar:Auth>
		            <ar:Token></ar:Token>
		            <ar:Sign></ar:Sign>
		            <ar:Cuit>20308152198</ar:Cuit>
		         </ar:Auth>
		         <ar:Periodo>201612</ar:Periodo>
		         <ar:Orden>1</ar:Orden>
		      </ar:FECAEASolicitar>
		   </soap:Body>
		</soap:Envelope>
         */

        // Armo el cuerpo del Xml que voy a enviar
        SOAPBody soapBody = envelope.getBody();
        SOAPElement solicitarSoapElement = soapBody.addChildElement("FECAEASolicitar", "ar");
                SOAPElement authSoapElement = solicitarSoapElement.addChildElement("Auth", "ar");
                	SOAPElement tokenSoapElement = authSoapElement.addChildElement("Token", "ar");
                	tokenSoapElement.addTextNode(autenticacion.getToken());
                	SOAPElement signSoapElement = authSoapElement.addChildElement("Sign", "ar");
                	signSoapElement.addTextNode(autenticacion.getSign());
                	SOAPElement cuitSoapElement = authSoapElement.addChildElement("Cuit", "ar");
                	cuitSoapElement.addTextNode("20308152198");
            	SOAPElement periodoSoapElement = solicitarSoapElement.addChildElement("Periodo", "ar");
            	periodoSoapElement.addTextNode("201612");
            	SOAPElement ordenSoapElement = solicitarSoapElement.addChildElement("Orden", "ar");
            	ordenSoapElement.addTextNode("2");
        
        //soapMessage.getSOAPPart().getEnvelope().removeAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "env");
        //soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("env");
        // Saco el atributo que no necesito
        soapMessage.getSOAPPart().getEnvelope().removeAttribute("xmlns:SOAP-ENV");
        // Cargo el prefijo a utilizar
        soapMessage.getSOAPPart().getEnvelope().setPrefix("soap");
        soapMessage.getSOAPBody().setPrefix("soap");
        soapMessage.getSOAPHeader().setPrefix("soap");
        
        // Guardo los cambios en el mensaje
        soapMessage.saveChanges();

        /* Print the request message */
        /*
        System.out.print("Request SOAP Message = ");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        soapMessage.writeTo(baos);
        baos.toString();
        System.out.println();
		*/
        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
    	
    	
    	NodeList returnList = soapResponse.getSOAPBody().getElementsByTagName("Errors");
    	
    	NodeList returnList2 = soapResponse.getSOAPBody().getElementsByTagName("Err");
    	
    	String codigoError = returnList2.item(0).getChildNodes().item(0).getTextContent();
    	String textoError = returnList2.item(0).getChildNodes().item(1).getTextContent();
    	
    	
    	System.out.println(textoError);
    	System.out.println(codigoError);
    	
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
        
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        soapResponse.writeTo(baos);
        baos.toString();
        System.out.println();
    }

}
