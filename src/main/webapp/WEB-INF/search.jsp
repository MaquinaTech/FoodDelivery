<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="search" lang="es">
<head>
  <meta charset="utf-8">
  <title>Search</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
  <script type="text/javascript" src="searchScript.js"></script>
  
</head>
  
  <body>
    <jsp:include page="/WEB-INF/include/header.jsp" />
    <div class="search">
      <div class="search_background" >
        <div class="search_card">
          <div class="search_card_text">
            <p class="p-search"><b>Pide lo que quieras</b></p>
            <p class="p-search"><b>¡Cuando quieras!</b></p>
          </div>
          <div class="search_card_bar">
            <form action="SearchServlet.do" method="GET" class="search_card_form" id="searchForm">
			    <input type="text" id="address" name="address" placeholder="Introduce tu dirección, Ej. Calle Alcalá, 6, Cáceres.">
			    <button id="searchButton" type="submit"><b>Search</b></button>
			</form>

          </div>
        </div>
      </div>
      <div class="search_categories">
        <p><b>Nuestras categorías</b></p>
        <div class="carousel">
          <div class="controls">
            <button id="prevBtn">&larr;</button>
            <button id="nextBtn">&rarr;</button>
          </div>
          <div class="search_slide">
            
		<c:forEach var="category" items="${categories}">
		    <div class="slide_card">
		        <a href='SearchServlet.do?category=${category.name}'>
				    <img src="${pageContext.request.contextPath}/public/${category.img}" alt="Category">
				</a>

		        <h1>${category.name}</h1>
		    </div>
		</c:forEach>

          </div>
        </div>
      </div>
    </div>

	<jsp:include page="/WEB-INF/include/footer.jsp" />
  </body>
  
  
  
</html>
  