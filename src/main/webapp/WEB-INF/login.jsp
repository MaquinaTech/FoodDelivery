<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="login" lang="es">
<head>
  <meta charset="utf-8">
  <title>Login</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
  
</head>
  <body>
    <div class="login-box">
      <img src="${pageContext.request.contextPath}/public/logo.png" class="avatar" alt="Logo">
      <h1>Iniciar sesión</h1>
      <form action="LoginServlet.do" method="POST">
        <label class="inputRed" for="username">Nombre de usuario</label>
        <input type="text" id="username" name="username" placeholder="Ingrese su nombre de usuario" required >
        <label class="inputGreen" for="password">Contraseña</label>
        <input type="password" id="password" name="password" placeholder="Ingrese su contraseña" required >
        <a href="#">¿Olvidaste tu contraseña?</a>
        <br>
        <input type="submit" value="Ingresar" >
      </form>
      <div style="display:grid">
        <a href="registro.html"> ¿Nuevo en Foodie? Crear cuenta</a>
      </div>
    </div>
  </body>
</html>