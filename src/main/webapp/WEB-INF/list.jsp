<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="list" lang="es">

<head>
	<meta charset="utf-8">
	<title>Lista Restaurantes</title>
	<link rel="shortcut icon" href="../logo.ico">
	<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />

</head>


<body>
	<jsp:include page="/WEB-INF/include/header.jsp" />

	<div class="wrapRestaurants">
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
		<div class="search_card_bar_list">
			<form action="SearchServlet.do" method="GET" class="search_card_form_list" id="searchForm">
			    <input type="text" id="address" name="address" placeholder="Introduce tu dirección, Ej. Calle Alcalá, 6, Cáceres.">
			    <button id="searchButton" type="submit"><b>Search</b></button>
			</form>
		</div>
		
		<div class="wrapList">
			<div class="dropdown">	
				<h2>Categorías</h2>
				<c:forEach var="category" items="${categories}" varStatus="loopStatus">
				    <c:choose>
				        <c:when test="${loopStatus.index < 3}">
				            <c:if test="${loopStatus.index == 0}">
				                <div class="dropdown-contentVisible">
				            </c:if>
				            <a href='SearchServlet.do?category=${category.name}'>${category.name}</a>
				            <c:if test="${loopStatus.index == 2}">
				  				</div>
				            </c:if>
				        </c:when>
			        <c:otherwise>
			        	<c:if test="${loopStatus.index == 3}">
			                <span class="dropdown-content">
			            </c:if>
			            <a href='SearchServlet.do?category=${category.name}'>${category.name}</a>			            
			            <c:if test="${loopStatus.index == 6}">
							</span>
						</c:if>            
			        </c:otherwise>
				    </c:choose>
				</c:forEach>				
				<button class="dropbtn">Más</button>
				
				<div class="estados">
					<h2>Filtro por Estado de Restaurante</h2>
        			<form method="post" action="SearchServlet.do" class="estado">
        			<label for="acepta" class="acepta"><h3>Todos</h3></label>
            		<input type="radio" id="all" name="estado" value="all" ${requestScope.all} required >
        			<label for="acepta" class="acepta"><h3>Acepta Pedidos</h3></label>
            		<input type="radio" id="acepta" name="estado" value="acepta" ${requestScope.acepta} required >
            		<label for="noacepta" class="noacepta"><h3>No acepta pedidos</h3></label>
            		<input type="radio" id="noacepta" name="estado" value="noacepta" ${requestScope.noacepta} >
            		<br></br>
    				<input type="submit" value="Buscar" id="noacepta">
        			</form>
				</div>
				
				<div class="valoracion">
					<h2>Filtro por Valoraciones</h2>
        			<form method="post" action="FiltroValoracionServlet.do" class="valoracion">
        			<label for="mayoramenor" class="mayoramenor"><h3>De mayor a menor</h3></label>
            		<input type="radio" id="mayoramenor" name="valoracion" value="mayor" ${requestScope.mayor} required >
            		<label for="menoramayor" class="menoramayor"><h3>De menor a mayor</h3></label>
            		<input type="radio" id="menoramayor" name="valoracion" value="menor" ${requestScope.menor} >
            		<br></br>
    				<input type="submit" value="Buscar">
        			</form>
				</div>
				
			</div>
			<div class="listRestaurants">
			<c:forEach var="restaurant" items="${restaurants}">
				<div class="card">
					<a href="restaurantDetailsServlet.do?name=${restaurant.name}">
						<img src="${pageContext.request.contextPath}/public/restaurant.png" alt="hamburguesaCategory" >						
					</a>
					<div class="details">
						<div class="description">
								<p class="title">
									<a href="restaurantDetailsServlet.do?idR=${restaurant.id}"><b>${restaurant.name}</b></a>
								</p>
							<div class="valoracion">
								<p>${restaurant.gradesAverage}</p>
								<img src="${pageContext.request.contextPath}/public/estrella.png" alt="calificación" >
							</div>
							<p class="subtitle">
								<b class="${restaurant.available == 1 ? '' : 'red'}">
								  ${restaurant.available == 1 ? 'Restaurante disponible en Food Delivery' : 'Restaurante no disponible'}
								</b>

							</p>
						</div>
						<div class="services">
							<div class="item"><img src="${pageContext.request.contextPath}/public/dinero.png" alt="dineroIcon">
								<p>${restaurant.minPrice}</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/dinero.png" alt="dineroIcon">
								<p>${restaurant.maxPrice}</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/send.png" alt="sendIcon">
								<p>Sin pedido mínimo </p>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/include/footer.jsp" />

	<script>
		//Lista de categorias
		const dropdown = document.querySelector('.dropdown');
		const dropdownContent = document.querySelector('.dropdown-content');
		const dropdownButton = document.querySelector('.dropbtn');
	
		dropdown.addEventListener('click', function () {
			dropdownContent.classList.toggle('open');
			dropdownButton.classList.toggle('open');
		});
	
		window.onclick = function (event) {
			if (!event.target.matches('.dropbtn')) {
				var dropdowns = document.getElementsByClassName("dropdown-content");
				for (var i = 0; i < dropdowns.length; i++) {
					var openDropdown = dropdowns[i];
					if (openDropdown.classList.contains('open')) {
						openDropdown.classList.remove('open');
						dropdownButton.classList.remove('open');
					}
				}
			}
		}
	
	
		//Carousel
		let slidePosition = 0;
		const slides = document.getElementsByClassName('search_slide')[0];
		const slidesLength = slides.querySelectorAll('div').length / 1.5;
		let interval;
		
	
		// Detener el intervalo cuando se coloca el cursor del ratón sobre el carrusel
		slides.addEventListener('mouseover', () => {
			clearInterval(interval);
		});
	
		// Volver a iniciar el intervalo cuando se quita el cursor del ratón del carrusel
		slides.addEventListener('mouseout', () => {
			startCarousel();
		});
	
		function startCarousel() {
			interval = setInterval(function () {
				nextSlide();
			}, 5000);
		}
	
		function nextSlide() {
			slidePosition = (slidePosition + 1) % slidesLength;
			slides.style.transform = `translateX(-${slidePosition * 17}%)`;
			for (let slide of slides.querySelectorAll('div')) {
				slide.classList.remove('active');
			}
			slides.querySelectorAll('div')[slidePosition].classList.add('active');
			
		}
	
		function prevSlide() {
			slidePosition = (slidePosition - 1 + slidesLength) % slidesLength;
			slides.style.transform = `translateX(-${slidePosition * 17}%)`;
	
			for (let slide of slides.querySelectorAll('div')) {
				slide.classList.remove('active');
			}
			slides.querySelectorAll('div')[slidePosition].classList.add('active');
		}
	
		// Iniciar el carrusel
		startCarousel();
	
		// Añadir eventos a los botones
		const prevButton = document.getElementById('prevBtn');
		const nextButton = document.getElementById('nextBtn');
	
		prevButton.addEventListener('click', prevSlide);
		nextButton.addEventListener('click', nextSlide);
	</script>

</body>

</html>