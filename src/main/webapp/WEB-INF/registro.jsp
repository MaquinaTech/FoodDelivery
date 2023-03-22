<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="register" lang="es">
<head>
  <meta charset="utf-8">
  <title>Registro</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
</head>
<body>
  <div class="registrobox">
    <form action="login.html" method="POST">
      <img src="../public/logo.png" class="avatar" alt="Logo">
      <h1>Registro de cuenta</h1>
      <div class="form-group">
          <label class="inputRed" for="nombre">Nombre:</label>
          <input type="text" id="nombre" name="nombre" required >
      </div>
      <div class="form-group">
          <label class="inputGreen" for="apellidos">Apellidos:</label>
          <input type="text" id="apellidos" name="apellidos" required >
      </div>
      <div class="form-group">
          <label class="inputRed" for="email">Correo electrónico:</label>
          <input type="email" id="email" name="email" required >
      </div>
      <div class="form-group">
          <label class="inputGreen" for="password">Contraseña:</label>
          <input type="password" id="password" name="password" required >
      </div>
      <div class="center">
        <input type="submit" value="Registrarse" >
      </div>
      
    </form>
    <div class="center">
      <a>Al crearte una cuenta, aceptas los Terminos y Condiciones de la pagina.</a>
      <a href="login.html"> ¿Ya tienes cuenta en Foodie?</a>
    </div>
  </div>
</body>
</html>
