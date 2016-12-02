<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="StyleSheet" href="css/menu.css" type="text/css">
		<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<title>Menu</title>
	</head>
	<body style="background-color: #D9D9D7;">
		<s:set name="menuRol" value="rol"/>

		<div>
			<ul id="accordion">
				<li>
			    	<div>Facturación</div>
			        <ul>
			        	<li><a href="iniciarCargarFactura.action" target="principal"><p>Facturar</p></a></li>
			        </ul>
			    </li>
			    <s:if test="%{#menuRol=='ADMIN'}">
				    <li>
				    	<div>Administración</div>
				        <ul>
							<li><a href="" target="principal"><p>Generar Recibos</p></a></li>
				        </ul>
				    </li>
			    </s:if>
			</ul>
		</div>

		<div id="salir" align="center">
			<a href="logout.action" target="_top" id="salirBoton">Salir</a>
			<br>
			<a href="pantallaUsuario.action" target="principal" id="usuario">Usuario: <s:property value="idUsuario"/></a>
			<span id="rol">Rol: <s:property value="rol"/></span>
			<br>
		</div>
	</body>
</html>
