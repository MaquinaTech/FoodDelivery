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
				        <a href="list.jsp">
				            <img src="${pageContext.request.contextPath}/public/${category.img}" alt="Category">
				        </a>
				        <h1>${category.name}</h1>
				    </div>
				</c:forEach>
			</div>
		</div>
		<div class="search_card_bar">
			<input type="text" id="searchBar" name="searchBar"
				placeholder="Introduce tu dirección, Ej. Calle Alcalá, 6, Madrid." >
				<a href="list.jsp"><button id="searchButton" name="searchButton"><b>Search</b></button></a>
		</div>
		<div class="wrapList">
			<div class="dropdown">
				<div class="dropdown-contentVisible">
					<a href="#">Hambuerguesería</a>
					<a href="#">Italiana</a>
					<a href="#">Mexicana</a>
					<span class="dropdown-content">
						<a href="#">kebab</a>
						<a href="#">sushi</a>
						<a href="#">india</a>
					</span>
				</div>
				<button class="dropbtn">Más</button>

			</div>
			<div class="listRestaurants">
			<c:forEach var="restaurant" items="${restaurants}">
				<div class="card">
					<a href="restaurantDetails.html">
						<img src="${pageContext.request.contextPath}/public/${restaurant.img}" alt="hamburguesaCategory" >
						
					</a>
					<div class="details">
						<div class="description">
							<a href="restaurantDetails.html">
								<p class="title"><b>${restaurant.name}</b></p>
							</a>
							<div class="valoracion">
								<p>4.8/5</p>
								<img src="${pageContext.request.contextPath}/public/estrella.png" alt="calificación" >
							</div>
							<p class="subtitle">
								<b>Hamburguesas, helados, patatas</b>
							</p>
						</div>
						<div class="services">
							<div class="item"><img src="${pageContext.request.contextPath}/public/reloj.png" alt="relojIcon">
								<p>25-40 min</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/dinero.png" alt="dineroIcon">
								<p>Entrega GRATIS</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/send.png" alt="sendIcon">
								<p>Sin pedido mínimo </p>
							</div>
						</div>
					</div>

				</div>
			</c:forEach>
				<div class="card">
					<a href="restaurantDetails.html">
						<img src="${pageContext.request.contextPath}/public/pizza.jpg" alt="pizaaCategory" >
					</a>
					<div class="details">
						<div class="description">
							<a href="restaurantDetails.html">
								<p class="title"><b>Telepizza</b></p>
							</a>
							<div class="valoracion">
								<p>4.6 / 5</p>
								<img src="${pageContext.request.contextPath}/public/estrella.png" alt="calificación" >
							</div>
							<p class="subtitle">
								<b>Pizzas, Calzone, Bebidas</b>
							</p>
						</div>
						<div class="services">
							<div class="item"><img src="${pageContext.request.contextPath}/public/reloj.png" alt="relojIcon">
								<p>25-40 min</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/dinero.png" alt="dineroIcon" >
								<p>Entrega GRATIS</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/send.png" alt="sendIcon" >
								<p>Sin pedido mínimo </p>
							</div>
						</div>
					</div>
				</div>
				<div class="card">
					<a href="restaurantDetails.html">
						<img src="${pageContext.request.contextPath}/public/sushi.jpg" alt="sushiCategory" >
					</a>
					<div class="details">
						<div class="description">
							<a href="restaurantDetails.html">
								<p class="title"><b>SushiMore</b></p>
							</a>
							<div class="valoracion">
								<p>4.2 / 5</p>
								<img src="${pageContext.request.contextPath}/public/estrella.png" alt="calificación" >
							</div>
							<p class="subtitle">
								<b>Rolls, Niguiris, Tartar</b>
							</p>
						</div>
						<div class="services">
							<div class="item"><img src="${pageContext.request.contextPath}/public/reloj.png" alt="relojIcon" >
								<p>25-40 min</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/dinero.png" alt="dineroIcon" >
								<p>Entrega GRATIS</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/send.png" alt="sendIcon" >
								<p>Sin pedido mínimo </p>
							</div>
						</div>
					</div>
				</div>
				<div class="card">
					<a href="restaurantDetails.html">
						<img src="${pageContext.request.contextPath}/public/italiano.jpg" alt="italianoCategory" >
					</a>
					<div class="details">
						<div class="description">
							<a href="restaurantDetails.html">
								<p class="title"><b>La Mafia</b></p>
							</a>
							<div class="valoracion">
								<p>4.0 / 5</p>
								<img src="${pageContext.request.contextPath}/public/estrella.png" alt="calificación" >
							</div>
							<p class="subtitle">
								<b>Pasta, Lasagna, Risoto</b>
							</p>
						</div>
						<div class="services">
							<div class="item"><img src="${pageContext.request.contextPath}/public/reloj.png" alt="relojIcon" >
								<p>25-40 min</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/dinero.png" alt="dineroIcon" >
								<p>Entrega GRATIS</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/send.png" alt="sendIcon" >
								<p>Sin pedido mínimo </p>
							</div>
						</div>
					</div>
				</div>
				<div class="card">
					<a href="restaurantDetails.html">
						<img src="${pageContext.request.contextPath}/public/kebab.jpg" alt="kebabCategory" >
					</a>
					<div class="details">
						<div class="description">
							<a href="restaurantDetails.html">
								<p class="title"><b>Durum Kbab</b></p>
							</a>
							<div class="valoracion">
								<p>3.5 / 5</p>
								<img src="${pageContext.request.contextPath}/public/estrella.png" alt="calificación" >
							</div>
							<p class="subtitle">
								<b>Kebab, Durum, Lamacum</b>
							</p>
						</div>
						<div class="services">
							<div class="item"><img src="${pageContext.request.contextPath}/public/reloj.png" alt="relojIcon" >
								<p>25-40 min</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/dinero.png" alt="dineroIcon" >
								<p>Entrega GRATIS</p>
							</div>
							<div class="item"><img src="${pageContext.request.contextPath}/public/send.png" alt="sendIcon" >
								<p>Sin pedido mínimo </p>
							</div>
						</div>
					</div>
				</div>
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