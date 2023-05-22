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
                    <div class="rating">
                    <p>${restaurant.gradesAverage}</p>
					    <input type="radio" id="star5" name="rating" value="5" disabled ${restaurant.gradesAverage == 5 ? 'checked' : ''}>
					    <label for="star5">&#9733;</label>
					    <input type="radio" id="star4" name="rating" value="4" disabled ${restaurant.gradesAverage == 4 ? 'checked' : ''}>
					    <label for="star4">&#9733;</label>
					    <input type="radio" id="star3" name="rating" value="3" disabled ${restaurant.gradesAverage == 3 ? 'checked' : ''}>
					    <label for="star3">&#9733;</label>
					    <input type="radio" id="star2" name="rating" value="2" disabled ${restaurant.gradesAverage == 2 ? 'checked' : ''}>
					    <label for="star2">&#9733;</label>
					    <input type="radio" id="star1" name="rating" value="1" disabled ${restaurant.gradesAverage == 1 ? 'checked' : ''}>
					    <label for="star1">&#9733;</label>
					</div>
				
                    <p><strong>Dirección:</strong> ${restaurant.address}</p>
                    <p><strong>Teléfono:</strong> ${restaurant.telephone}</p>
                    <p><strong>Correo de contacto:</strong> ${restaurant.contactEmail}</p>
                    <p><strong>Rango de precio:</strong> ${restaurant.minPrice}€-${restaurant.maxPrice}€</p>
                    <p><strong>Media de valoraciones:</strong> ${restaurant.gradesAverage}</p>
                    <p><strong>Bike Friendly:</strong> <input type="checkbox" value="${restaurant.bikeFriendly}" ${restaurant.bikeFriendly == 1 ? 'checked' : ''} disabled></p>
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
                	<c:choose>
		    			<c:when test="${restaurant.idu==id}">
		    				<a href="dishServlet.do?idR=${restaurant.id}"><img width="50px" src="${pageContext.request.contextPath}/public/add.png" alt="addIcon"></a>
		    			</c:when>
		    		</c:choose>
                    <h1>Platos</h1>
                    <c:forEach var="dish" items="${dishes}">
                        <div class="dish-card">
                            <div class="dish-info">
							    <c:choose>
							        <c:when test="${restaurant.idu == id}">
							            <form action="dishServlet.do" method="POST">
							                <input type="text" name="dishName" value="${dish.name}" required>
							                <input type="text" name="dishDescription" value="${dish.description}" required>
							                <input type="number" name="dishPrice" value="${dish.price}" required>
							                <input class="none" type="number" name="idR" value="${restaurant.id}">
							                <input class="none" type="number" name="idD" value="${dish.id}">
							                <input type="submit" value="Editar">
							            </form>
							            <form action="dishDeleteServlet.do" method="POST">
							                <input class="none" type="number" name="idD" value="${dish.id}">
							                <input class="none" type="number" name="idR" value="${restaurant.id}">
							                <button type="submit">Eliminar</button>
							            </form>
							        </c:when>
							        <c:otherwise>
							            <h3>${dish.name}</h3>
							            <p>${dish.description}</p>
							            <p class="price">${dish.price}€</p>
							        </c:otherwise>
							    </c:choose>
							</div>

                        	
                        </div>
                   </c:forEach>    
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

