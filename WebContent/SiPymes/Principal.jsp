<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>LSC</title>
		<link rel="Shortcut Icon" href="imagenes/Icono.png" type="image/x-icon">
	</head>
	<frameset cols="170px,*" bordercolor="#D9D9D7" style="margin-right: 20px;">
		<frame name="menu" id="menuId" src="<s:url value="cargarMenu.action"/>" noresize="noresize" scrolling="false">
		<frame name="principal" id="principalId" src="<s:url value="SiPymes/Inicio.jsp"/>">
	</frameset>

	<NOFRAMES>
		<h1 id="titulo">Debes actualizar tu navegador =S</h1>
	</NOFRAMES>
</html>