/**
 * 
 */

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