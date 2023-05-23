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
                <div class="editbox">
                  <div class="dish">
			        <form action="dishServlet.do" method="POST">
			          <h2>Nuevo Plato</h2>
			          <label>Nombre:</label>
			          <input required type="text" name="dishName" placeholder="Nombre del plato">
			          <input class="none" type="number" name="idR" value="${idR}">
			          
			          <label>Descripción:</label>
			          <textarea required name="dishDescription" placeholder="Descripción del plato"></textarea>
			          <label>Precio:</label>
			          <input required type="number" name="dishPrice" placeholder="Precio del plato (no se permiten decimales)">
			          
			          <div class="center">
				        <input type="submit" value="Añadir" >
				      </div>
			        </form>
			      </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/include/footer.jsp"/>

 <script>

    // Rango de precios minimo y maximo
    const rangeMin = document.getElementById('range-min');
    const rangeMax = document.getElementById('range-max');
    const rangeValueMin = document.getElementById('range-value-min');
    const rangeValueMax = document.getElementById('range-value-max');

    rangeValueMin.textContent = rangeMin.value;
    rangeValueMax.textContent = rangeMax.value;

    rangeMin.addEventListener('input', function() {
      rangeValueMin.textContent = this.value;
    });

    rangeMax.addEventListener('input', function() {
      rangeValueMax.textContent = this.value;
    });


    //Add new dish
    //TODO controlar id de los input para enviarlos por el form sin duplicados con un indice
    const addNewDishBtn = document.getElementById('addNewDish');
    const deleteLastDishBtn = document.getElementById('deleteLastDish');
    const dish = document.getElementById('dish');

    addNewDishBtn.addEventListener('click', function() {
      const newDish = document.createElement('div');
      newDish.innerHTML = `
          <h2>Nuevo Plato</h2>
          <br>
          <label>Nombre:</label>
          <input type="text" name="dishName">
          <label>Descripción:</label>
          <textarea name="dishDescription"></textarea>
          
          <label>Precio:</label>
          <input type="number" name="dishPrice">
      `;
      const lastDish = dish.lastChild;
      dish.insertBefore(newDish, lastDish.nextSibling);
    });


    //Delete last dish added
    deleteLastDishBtn.addEventListener('click', function() {
      const lastDish = dish.lastChild;
      if (lastDish) {
        dish.removeChild(lastDish);
      }
    });

  </script>
</body>
</html>

