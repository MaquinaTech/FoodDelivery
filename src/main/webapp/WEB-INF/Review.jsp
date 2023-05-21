<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="Review" lang="es">
<head>
  <meta charset="utf-8">
  <title>Review</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
  <script>
    function updateRangeValue() {
      var rangeInput = document.getElementById("review");
      var rangeValueDisplay = document.getElementById("rangeValue");
      rangeValueDisplay.textContent = rangeInput.value;
    }
  </script>
</head>
<body>
   <div class="editbox">
       <h1>Crear Review</h1>
       <h2>${requestScope.error}</h2>
       <form action="reviewServlet.do?id=${restaurant.id}" method="POST">
           <div class="review">
               <label for="review">Selecciona el rango:</label>
               <p id="rangeValue">0</p>
               <input type="range" id="review" name="review" value="grade" min="0" max="5" required oninput="updateRangeValue()">
               <label for="comentario">Deja tu comentario:</label>
               <input type="text" id="comentario" name="comentario" required>    
           </div>
           <button type="submit">Enviar</button>
       </form>      
   </div>
</body>
</html>
