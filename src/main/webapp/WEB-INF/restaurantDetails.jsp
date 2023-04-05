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
            <ul>
                <li style="--color:#9e2229">
                  <a href="#principales" data-text="&nbsp;Principales">&nbsp;Principales&nbsp;</a>
                </li>
                <li style="--color:#397b54">
                  <a href="#complementos" data-text="&nbsp;Complementos">&nbsp;Complementos&nbsp;</a>
                </li>
                <li style="--color:#9e2229">
                  <a href="#postres" data-text="&nbsp;Postres">&nbsp;Postres&nbsp;</a>
                </li>
                <li style="--color:#397b54">
                  <a href="#bebidas" data-text="&nbsp;Bebidas">&nbsp;Bebidas&nbsp;</a>
                </li>
            </ul>
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
                    <p><strong>Categoría:</strong> ${category.name}</p>
                    <p><strong>Media de valoraciones:</strong> 4/5</p>
                    <p><strong>Bike Friendly:</strong> <input disabled type="checkbox" value="${restaurant.bikeFriendly}"></p>
                    <% if (request.getSession().getAttribute("username") != null) { %>
                    	<a href="restaurantEditServlet.do?idR=${restaurant.id}"><img src="${pageContext.request.contextPath}/public/editar.png" alt="editIcon"></a>
                    <% } %>
                </div>
                <div class="menuList">
                    <section id="principales">
                    <c:forEach var="dish" items="${dishes}">
                        <h1>Principales</h1>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/whopper.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>${dish.name}</h3>
                            <p>${dish.description}</p>
                            <p class="price">${dish.price}€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            
                            </div>
                        </div>
                   </c:forEach>
                    </section>
                    <section id="complementos">
                        <h1>Complementos</h1>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/patatasnormales.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>Patatas clasicas</h3>
                            <p>Las famosas patatas fritas de las que tanto has oído hablar, si estás son, las mejores, las más crujientes, 
                                las que tienen más sabor, si las pruebas entenderás el por qué de su fama. Disponibles en varios tamaños.</p>
                            <p class="price">3€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            </div>
                        </div>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/patatassupreme.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>Patatas Supreme</h3>
                            <p>Ahora puedes acompañar tus menús con las deliciosas Patatas Supreme. Pruébalas además con la nueva salsa Sour Cream.</p>
                            <p class="price">3€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            </div>
                        </div>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/nuggets.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>Chicken Nuggets </h3>
                            <p>Nuevos deliciosos, crujientes, dorados… dipealos en su sabrosa salsa. disponibles en 6, 9 ó 20 uds.</p>
                            <p class="price">3€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            </div>
                        </div>
                    </section>
                    <section id="postres">
                        <h1>Postres</h1>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/tarta.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>Tarta de Queso </h3>
                            <p>Recuerda calentarlo 20 segundos en el microondas para disfrutarlo al 100%</p>
                            <p class="price">4€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            </div>
                        </div>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/benandjerry.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>Ben & Jerrys Chocolate Fudge 465 ML </h3>
                            <p>Si eres amante del chocolate, este postre te volverá loco. Ben & Jerry,s Chocolate Fudge Brownie es la elección perfecta 
                                para terminar una gran comida. Solo imagina una base de helado de chocolate con trozos de brownie, ¿te resistirás?. (465 ml)</p>
                            <p class="price">8€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            </div>
                        </div>
                    </section> 
                    <section id="bebidas">
                        <h1>Bebidas</h1>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/cocacola.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>Cocacola </h3>
                            <p>Coca-Cola Sabor Original botella 500ml. Envase 100% reciclable.</p>
                            <p class="price">3€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            </div>
                        </div>
                        <div class="dish-card">
                            <img src="${pageContext.request.contextPath}/public/fanta.png" alt="Imagen del plato">
                            <div class="dish-info">
                            <h3>Fanta de Naranja </h3>
                            <p>Fanta Naranja botella 500ml. Envase 100% reciclable.</p>
                            <p class="price">3€ <img class = "Cesta-pedido" src="${pageContext.request.contextPath}/public/cesta.png" alt="Cesta"></p>
                            </div>
                        </div>
                    </section>  
                </div>
            </div>
            <div class="pedido-box">
                <h1>Tu pedido</h1>
                <form action="#" method="POST">
                    <div class="pedido">
                        <label>
                            <input type="radio" name="envio" value="recoger" checked> Pedir para recoger
                        </label>
                        <label>
                            <input type="radio" name="envio" value="domicilio"> Pedir a domicilio
                        </label>
                    </div>
                    <button type="submit">Pagar</button>
                </form>      
            </div>
            <div class="logo_details">
                <img src="${pageContext.request.contextPath}/public/burguerking.png" alt="logoBurguerKing">
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/include/footer.jsp" />
      
    <script>
    
        const form = document.querySelector('form');
        const button = form.querySelector('button[type="submit"]');
    
        form.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const envio = form.querySelector('input[name="envio"]:checked').value;
        
        // aquí se puede realizar alguna acción dependiendo del valor seleccionado (envio)
        alert(`Ha seleccionado ${envio}`);
        });
    
    </script>

</body>
</html>

