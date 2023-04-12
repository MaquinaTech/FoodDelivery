<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="UserEdit" lang="es">
<head>
  <meta charset="utf-8">
  <title>EliminarCuenta</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
</head>
    <body>
        <form class="editbox" method="post" action="?">
            <h1>¿Estás seguro de que quieres eliminar tu cuenta?</h1>
            <label for="si">Sí</label>
            <input type="radio" id="si" name="respuesta" value="Si" required>
            <label for="no">No</label>
            <input type="radio" id="no" name="respuesta" value="No">
            <input type="submit" value="Confirmar respuesta" name="confirmar" class="center">
            <input type="hidden" name="id" value="${hosting.id}">
        </form>
    </body>
</html>