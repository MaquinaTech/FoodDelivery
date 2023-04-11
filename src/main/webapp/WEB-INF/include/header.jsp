<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
</head>
	<header>
      <div class="logo">
      <img src="${pageContext.request.contextPath}/logo.ico" alt="Logo de mi sitio web">
      </div>
      <nav class="header-nav">
      <ul>
        <li><a href="search.html">Inicio</a></li>
      	<li><a href="LoginServlet.do">¡Bienvenido ${empty sessionScope.username ? "Iniciar sesión" : sessionScope.username}!</a></li>
        <li><a href="UserEditServlet.do?id=${sessionScope.id}"><img width="25px" src="${pageContext.request.contextPath}/public/editar.png" alt="editIcon"></a></li>
      	<li><a href="#">Carrito de compra</a></li>
      </ul>
      </nav>
    </header>
<body>

</body>
</html>