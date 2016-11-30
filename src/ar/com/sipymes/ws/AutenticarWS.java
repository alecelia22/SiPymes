package ar.com.sipymes.ws;

import java.io.FileInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;
import org.apache.axis.encoding.XMLType;
import org.apache.struts2.ServletActionContext;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class AutenticarWS {
	
	public Autenticacion obtenerWSAA() {
		Autenticacion autenticacion = new Autenticacion();

		// WSDL al cual acceder
		String endpoint = "https://wsaahomo.afip.gov.ar/ws/services/LoginCms?wsdl";
		// DN de destino		
		String dstDN = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239";

		
		// Obtengo una direccion como esta: C:\jboss-as-7.1.1.Final\standalone 
		ServletContext context= ServletActionContext.getServletContext();
		String p12File = context.getRealPath("/WEB-INF/classes/afip/homo") + "/";
		
		
		// Certificado final
		//String p12File = "C:/temp/certs/afip/homo/cert.p12";

		// Firmante del certificado
		String signer = "signerName";
		// Password del certificado
		String p12pass = "passLocalDelP12";

		// Configuracion del Proxy, en caso de ser necesaria
		System.setProperty("http.proxyHost", "proxylatam.indra.es");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("http.proxyUser", "ajcelia");
		System.setProperty("http.proxyPassword", "Bichos17+");
		
		// Create LoginTicketRequest_xml_cms
		byte [] loginTicketRequestXmlCms = this.createCms(p12File, p12pass, signer, dstDN);

		// Invoke AFIP wsaa and get LoginTicketResponse
		String loginTicketResponse = "";

		try {
			
			loginTicketResponse = this.invokeWsaa(loginTicketRequestXmlCms, endpoint);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Get token & sign from LoginTicketResponse
		try {
			// Cargo los datos devueltos
			Reader tokenReader = new StringReader(loginTicketResponse);
			Document tokenDoc = new SAXReader(false).read(tokenReader);

			// Cargo los valores de autenticacion
			autenticacion.setToken(tokenDoc.valueOf("/loginTicketResponse/credentials/token"));
			autenticacion.setSign(tokenDoc.valueOf("/loginTicketResponse/credentials/sign"));

		} catch (Exception e) {
			System.out.println(e);
		}

		return autenticacion;
	}

	/**
	 * 
	 * @param p12file Certificado
	 * @param p12pass Password del certificado
	 * @param signer Firmante del certificado
	 * @param dstDN DN de destino
	 * @param service Servicio a solicitar
	 * @param TicketTime
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static byte [] createCms (String p12file, String p12pass, String signer, String dstDN) {
		PrivateKey privateKey = null;
		X509Certificate x509Certificate = null;
		byte [] asn1_cms = null;
		CertStore cstore = null;
		String loginTicketRequestXml;
		String SignerDN = null;

		//
		// Manage Keys & Certificates
		//
		try {
			// Create a keystore using keys from the pkcs#12 p12file
			KeyStore keyStore = KeyStore.getInstance("pkcs12");
			FileInputStream p12stream = new FileInputStream(p12file) ;
			keyStore.load(p12stream, p12pass.toCharArray());
			p12stream.close();

			// Get Certificate & Private key from KeyStore
			privateKey = (PrivateKey) keyStore.getKey(signer, p12pass.toCharArray());
			x509Certificate = (X509Certificate)keyStore.getCertificate(signer);
			SignerDN = x509Certificate.getSubjectDN().toString();

			// Create a list of Certificates to include in the final CMS
			ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>();
			certList.add(x509Certificate);

			if (Security.getProvider("BC") == null) {
				Security.addProvider(new BouncyCastleProvider());
			}

			cstore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 

		// Create XML Message
		loginTicketRequestXml = createLoginTicketRequest(SignerDN, dstDN);
		
		// Create CMS Message
		try {
			// Create a new empty CMS Message
			CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

			// Add a Signer to the Message
			gen.addSigner(privateKey, x509Certificate, CMSSignedDataGenerator.DIGEST_SHA1);

			// Add the Certificate to the Message
      		gen.addCertificatesAndCRLs(cstore);

			// Add the data (XML) to the Message
			CMSProcessable data = new CMSProcessableByteArray(loginTicketRequestXml.getBytes());

			// Add a Sign of the Data to the Message
			CMSSignedData signed = gen.generate(data, true, "BC");	

			// 
			asn1_cms = signed.getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
		return (asn1_cms);
	}
	
	/**
	 * 
	 * @param SignerDN 
	 * @param dstDN
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static String createLoginTicketRequest (String SignerDN, String dstDN) {
		Long TicketTime = 36000L;

		// Fecha de generacion 
		GregorianCalendar generationTime = new GregorianCalendar();
		generationTime.setTime(new Date(System.currentTimeMillis()));

		// Fecha de expirado
		Date genDate = new Date();
		GregorianCalendar expirationTime = new GregorianCalendar();
		String UniqueId = new Long(genDate.getTime() / 1000).toString();
		expirationTime.setTime(new Date(genDate.getTime() + TicketTime));
		
		
		//Convierto las fechas al formato necesario 
		XMLGregorianCalendar XMLGenerationTime = null;
		XMLGregorianCalendar XMLExpirationTime = null;
		
		try {
			XMLGenerationTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(generationTime);
			XMLExpirationTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(expirationTime);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			// Tengo que ver de armar bien este error
			e.printStackTrace();
		}
		
		String loginTicketRequestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
			"<loginTicketRequest version=\"1.0\">" +
			"<header>" +
			"<source>" + SignerDN + "</source>" +
			"<destination>" + dstDN + "</destination>" +
			"<uniqueId>" + UniqueId + "</uniqueId>" +
			"<generationTime>" + XMLGenerationTime + "</generationTime>" +
			"<expirationTime>" + XMLExpirationTime + "</expirationTime>" +
			"</header>" +
			"<service>wsfe</service>" +
			"</loginTicketRequest>";

		//System.out.println("TRA: " + loginTicketRequestXml);
		
		return (loginTicketRequestXml);
	}

	/**
	 * 
	 * @param loginTicketRequestXmlCms
	 * @param endpoint
	 * @return
	 * @throws ServiceException 
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 * @throws Exception
	 */
	static String invokeWsaa (byte [] loginTicketRequestXmlCms, String endpoint) throws Exception {
		String loginTicketResponse = null;
		  
		Service service = new Service();


		Call call = (Call) service.createCall();
	
		//
		// Prepare the call for the Web service
		//
		call.setTargetEndpointAddress( new java.net.URL(endpoint) );
		call.setOperationName("loginCms");
		call.addParameter( "request", XMLType.XSD_STRING, ParameterMode.IN );
		call.setReturnType( XMLType.XSD_STRING );
	
		//
		// Make the actual call and assign the answer to a String
		//
		loginTicketResponse = (String) call.invoke(new Object [] {Base64.encode(loginTicketRequestXmlCms) } );

		return loginTicketResponse;
		
	}
}
