<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="UserEdit" lang="es">
<head>
  <meta charset="utf-8">
  <title>Editar Usuario</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
</head>

<body>
  <jsp:include page="/WEB-INF/include/header.jsp" />

  <div class="editbox">
    <form action="UserEditServlet.do" method="POST">
      <br><br>
      <h1>Configurar Usuario</h1>
      <div class="form-group">
          <label class="username" for="nombre">Nombre</label>
          <input type="text" id="name" name="name" required value="${user.name}">
      </div>
      <div class="form-group">
        <label class="username" for="apellido">Apellidos</label>
        <input type="text" id="surname" name="surname" required value= "${user.surname}">
      </div>
      <div class="form-group">
          <label class="username" for="email">Correo electrónico</label>
          <input type="email" id="email" name="email" required value="${user.email}">
      </div>
      <div class="form-group">
          <label class="username" for="password">Nombre</label>
          <input type="text" id="password" name="name" required value="${user.password}">
      </div>      
      <div class="center">
        <input type="submit" value="Editar" >
      </div>    
    </form>    
  </div>
  <jsp:include page="/WEB-INF/include/footer.jsp" />  
</body>
</html>