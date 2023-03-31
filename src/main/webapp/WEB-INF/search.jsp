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
            <form action="searchServlet.do" method="GET" class="search_card_form" id="searchForm">
			    <input type="text" id="searchBar" name="searchBar" placeholder="Introduce tu dirección, Ej. Calle Alcalá, 6, Cáceres.">
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
            

            <div class="slide_card">
              <a href="list.html">
                <img src="${pageContext.request.contextPath}/public/hamburguesa.jpg" alt="hamburguesaCategory">
              </a>
              <h1>Burguer</h1>
            </div>
            <div class="slide_card">
              <a href="list.html">
                <img src="${pageContext.request.contextPath}/public/pizza.jpg" alt="pizzaCategory">
              </a>
              <h1>Pizza</h1>
            </div>
            <div class="slide_card">
              <a href="list.html">
                <img src="${pageContext.request.contextPath}/public/sushi.jpg" alt="sushiCategory">
              </a>
              <h1>Sushi</h1>
            </div>
            <div class="slide_card">
              <a href="list.html">
                <img src="${pageContext.request.contextPath}/public/italiano.jpg" alt="italianoCategory">
              </a>
              <h1>Italiano</h1>
            </div>
            <div class="slide_card">
              <a href="list.html">
                <img src="${pageContext.request.contextPath}/public/kebab.jpg" alt="kebabCategory">
              </a>
              <h1>Kebab</h1>
            </div>
            <div class="slide_card">
              <a href="list.html">
                <img src="${pageContext.request.contextPath}/public/pollo.jpg" alt="polloCategory">
              </a>
              <h1>Pollo</h1>
            </div>
          </div>
        </div>
      </div>
    </div>

	<jsp:include page="/WEB-INF/include/footer.jsp" />
  </body>
  
  
  
</html>
  