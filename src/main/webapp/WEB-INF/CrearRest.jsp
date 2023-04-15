<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="CrearRest" lang="es">
<head>
  <meta charset="utf-8">
  <title>CrearRest</title>
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
</head>
<body>
  <div class="registrobox">
    <form action="CrearRestServlet.do" method="POST">
      <h1>Rellenar datos</h1>
      <div class="form-group">
          <label class="inputRed" for="nombre">Nombre:</label>
          <input type="text" id="firstName" name="name" required >
      </div>
      <div class="form-group">
          <label class="inputGreen" for="address">Dirección:</label>
          <input type="text" id="address" name="address" required >
      </div>
      <div class="form-group">
          <label class="inputRed" for="nombre">Telefono:</label>
          <input type="number" id="telephone" name="telephone" required >
      </div>
      <div class="form-group">
          <label class="inputGreen" for="gradesAverage">Valoración:</label>
          <input type="number" id="gradesAverage" name="gradesAverage" required >
      </div>
      <div class="form-group">
          <label class="inputRed" for="city">Ciudad:</label>
          <input type="text" id="city" name="city" required >
      </div>
      <div class="form-group">
          <label class="inputGreen" for="minPrice">Precio Mínimo:</label>
          <input type="number" id="minPrice" name="minPrice" required >
      </div>
      <div class="form-group">
          <label class="inputRed" for="maxPrice">Precio Máximo:</label>
          <input type="number" id="maxPrice" name="maxPrice" required >
      </div>
      <div class="form-group">
          <label class="inputGreen" for="email">Correo electrónico:</label>
          <input type="email" id="email" name="email" required >
      </div>
      <div class="form-group">
          <label class="inputRed" for="bikeFriendly">Bike Friendly:</label>
          <input type="radio" id="bikeFriendly" name="bikeFriendly" value= "0" required >0
          <input type="radio" id="bikeFriendly" name="bikeFriendly" value= "1" required >1
      </div>
      <div class="form-group">
          <label class="inputGreen" for="available">Restaurante disponible:</label>
          <input type="radio" id="available" name="available" value= "0" required >0
          <input type="radio" id="available" name="available" value= "1" required >1
      </div>
      <div class="form-group">
          <label class="inputRed" for="subtitulo">Subtitulo:</label>
          <input type="text" id="subtitulo" name="subtitulo" required >
      </div>
      <div class="form-group">
          <label class="inputGreen" for="img">Logo:</label>
          <input type="text" id="img" name="img">
      </div>
      <div class="center">
        <input class="inputSubmit" type="submit" value="Crear" >
      </div>      
    </form>
  </div>
</body>
</html>