<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="search" lang="es">
<head>
  <meta charset="utf-8">
  <title>Search</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
  
</head>
  
  <body>
    <header>
      <div class="logo">
      <img src="${pageContext.request.contextPath}/logo.ico" alt="Logo de mi sitio web">
      </div>
      <nav class="header-nav">
      <ul>
        <li><a href="search.html">Inicio</a></li>
        <li><a href="login.html">Cerrar sesión</a></li>
        <li><a href="#">Carrito de compra</a></li>
      </ul>
      </nav>
    </header>
    <div class="search">
      <div class="search_background" >
        <div class="search_card">
          <div class="search_card_text">
            <p class="p-search"><b>Pide lo que quieras</b></p>
            <p class="p-search"><b>¡Cuando quieras!</b></p>
          </div>
          <div class="search_card_bar">
            <input type="text" id="searchBar" name="searchBar" placeholder="Introduce tu dirección, Ej. Calle Alcalá, 6, Madrid.">
            <a href="list.html"><button id="searchButton" name="searchButton"><b>Search</b></button></a>
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

    <footer>
      <div class="logo">
        <img src="../logo.ico" alt="Logo de mi sitio web">
      </div>
      <nav class="footer-nav">
        <ul>
          <li><a href="#">Sobre nosotros</a></li>
          <li><a href="#">Ayuda</a></li>
          <li><a href="#">Contacto</a></li>
          <li><a href="#">Email</a></li>
        </ul>
      </nav>
    </footer>
  
    <script>
    document.addEventListener('DOMContentLoaded', function() {
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
        interval = setInterval(function() {
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
  	});
    </script>
  </body>
  
  
  
</html>
  