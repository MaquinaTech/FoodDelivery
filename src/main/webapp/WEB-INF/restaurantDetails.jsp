<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="restaurantDetails" lang="es">
<head>
  <meta charset="utf-8">
  <title>Detalle Restaurante</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
  
</head>

<body>
    <jsp:include page="/WEB-INF/include/header.jsp" />
	
    <div class="details">
        <div class="details_background" >
            <div class="info-menu">
                <div class="info-box">
                    <br>
                    <br>
                    <br>
                    <h1>Información del restaurante</h1>
                    <h2>${restaurant.name}</h2>
                    <% if (request.getSession().getAttribute("username") != null) { %>
	                    <div class="rating">
						    <input type="radio" id="star5" name="rating" value="5" ${restaurant.gradesAverage == 5 ? 'checked' : ''}>
						    <label for="star5">&#9733;</label>
						    <input type="radio" id="star4" name="rating" value="4" ${restaurant.gradesAverage == 4 ? 'checked' : ''}>
						    <label for="star4">&#9733;</label>
						    <input type="radio" id="star3" name="rating" value="3" ${restaurant.gradesAverage == 3 ? 'checked' : ''}>
						    <label for="star3">&#9733;</label>
						    <input type="radio" id="star2" name="rating" value="2" ${restaurant.gradesAverage == 2 ? 'checked' : ''}>
						    <label for="star2">&#9733;</label>
						    <input type="radio" id="star1" name="rating" value="1" ${restaurant.gradesAverage	 == 1 ? 'checked' : ''}>
						    <label for="star1">&#9733;</label>
						</div>
					<% } else { %>
					    <div class="rating">
					        <input type="radio" id="star5" name="rating" value="5" disabled>
					        <label for="star5">&#9733;</label>
					        <input type="radio" id="star4" name="rating" value="4" disabled>
					        <label for="star4">&#9733;</label>
					        <input type="radio" id="star3" name="rating" value="3" disabled>
					        <label for="star3">&#9733;</label>
					        <input type="radio" id="star2" name="rating" value="2" disabled>
					        <label for="star2">&#9733;</label>
					        <input type="radio" id="star1" name="rating" value="1" disabled>
					        <label for="star1">&#9733;</label>
					    </div>
					<% } %>
                    
                    <p><strong>Dirección:</strong> ${restaurant.address}</p>
                    <p><strong>Teléfono:</strong> ${restaurant.telephone}</p>
                    <p><strong>Correo de contacto:</strong> ${restaurant.contactEmail}</p>
                    <p><strong>Rango de precio:</strong> ${restaurant.minPrice}€-${restaurant.maxPrice}€</p>
                    <p><strong>Media de valoraciones:</strong> ${restaurant.gradesAverage}</p>
                    <p><strong>Bike Friendly:</strong> <input disabled type="checkbox" value="${restaurant.bikeFriendly}"></p>
                    <c:choose>
		    			<c:when test="${restaurant.idu==id}">
		    					<a href="restaurantEditServlet.do?idR=${restaurant.id}"><img src="${pageContext.request.contextPath}/public/editar.png" alt="editIcon"></a>
		    				</c:when>
		    		</c:choose>
		    		<c:choose>
		    			<c:when test="${restaurant.idu!=id}">
		    					<a href="reviewServlet.do?idR=${restaurant.id}">Añadir Review</a>
		    				</c:when>
		    		</c:choose>
		    			
                </div>
                <div class="menuList">
                    <section id="principales">
                    <h1>Platos</h1>
                    <c:forEach var="dish" items="${dishes}">
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/${dish.img}" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>${dish.name}</h3>
                            <p>${dish.description}</p>
                            <p class="price">${dish.price}€</p>
                            
                            </div>
                        </div>
                   </c:forEach>
                    </section>                    
                </div>
            </div>
            <div class="pedido-box">
                <a href="pedidoServlet.do?idR=${restaurant.id}">Realizar Pedido</a>          
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/include/footer.jsp"/>

</body>
</html>

