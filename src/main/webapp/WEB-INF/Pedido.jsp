<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="Pedido" lang="es">
<head>
  <meta charset="utf-8">
  <title>Pedido</title>
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
</head>
<body>
  <div class="registrobox">
     <h1 id= "h1pedido">Tu pedido</h1>      
     <form action="pedidoServlet.do" method="POST">
     	<h2>${requestScope.error}</h2>
         <div class="pedido">
         	<c:forEach var="dish" items="${dishes}">
             <label for= "pedido">${dish.name}</label>
                 <input type="checkbox" id= "pedido" name="pedido" value="${dish.name}">
          	</c:forEach>
         </div>
         <input type="submit" value="Pagar">
     </form>
  </div>
</body>
</html>