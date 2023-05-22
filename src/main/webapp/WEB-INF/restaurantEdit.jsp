<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="restaurantEdit" lang="es">
<head>
  <meta charset="utf-8">
  <title>Editar Restaurante</title>
  <link rel="shortcut icon" href="../logo.ico">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css"   />
</head>

<body>
  <jsp:include page="/WEB-INF/include/header.jsp" />

  <div class="editbox">
    <form action="restaurantEditServlet.do" method="POST">
      <img src="${pageContext.request.contextPath}/public/burguerking.png" alt="logoBurguerKing">
      <br><br>
      <h1>Datos Restaurante</h1>
      <p>${empty requestScope.error ? "" : requestScope.error}</p>
      <div class="form-group">
          <label class="username" for="nombre">Nombre</label>
          <input type="text" id="name" name="name" required value="${restaurant.name}">
      </div>
      <div class="form-group">
        <label class="username" for="direccion">Dirección</label>
        <textarea id="address" name="address" required >${restaurant.address}</textarea>
      </div>
      <div class="form-group">
          <label class="username" for="phone">Teléfono</label>
          <input type="tel" id="telephone" name="telephone" required value="${restaurant.telephone}" >
      </div>
      <div class="form-group">
          <label class="username" for="email">Correo electrónico</label>
          <input type="email" id="email" name="email" required value="${restaurant.contactEmail}">
          <input class="none" type="email" id="emailOLD" name="emailOLD" value="${restaurant.contactEmail}">
      </div>
      <br><br>
      <h2>Rango de Precio</h2>
      <div class="input_flex">
        <div>
          <p>Mínimo:</p>
          <input type="range" id="range-min" name="range-min" min="0" max="100" value="${restaurant.minPrice }">
          <p id="range-value-min"></p>
        </div>
        <div>
          <p>Máximo:</p>
          <input type="range" id="range-max" name="range-max" min="0" max="100" value="${restaurant.maxPrice }">
          <p id="range-value-max"></p>
        </div>
      </div>
      <h2>Categoría:</h2>
      <div class="input_flex">
      	<select name="categorias">
		  <c:forEach var="category" items="${categories}">
		    <option value="${category.name}" <c:if test="${categoryName != null && category.name == categoryName}">selected</c:if>>${category.name}</option>
		  </c:forEach>
		</select>


      </div>

      <br><br>
      <h2>¿Bike Friendly?</h2>
      <div class="input_flex">
	      <select name="bikeFriendly">
			  <option value="1" ${restaurant.bikeFriendly == 1 ? 'selected' : ''}>Sí</option>
			  <option value="0" ${restaurant.bikeFriendly == 0 ? 'selected' : ''}>No</option>
		  </select>
	  </div>


      
      <br><br>
      <h2>¿Acepta pedidos?</h2>
      <div class="input_flex">
        <select name="available">
		  <option value="1" ${restaurant.available == 1 ? 'selected' : ''}>Sí</option>
		  <option value="0" ${restaurant.available == 0 ? 'selected' : ''}>No</option>
		</select>
      </div>
      
      <a id="eliminar"  href="<c:url value='EliminarRestauranteServlet.do?id=${restaurant.id}'/>">Eliminar Restaurante</a>

      <div class="dish" id="dish">
      	<c:forEach var="dish" items="${dishes}">
	        <div>
	          <h2>Nuevo Plato</h2>
	          <label>Nombre:</label>
	          <input type="text" name="dishName" id="${dish.id}" placeholder="Nombre del plato" value="${dish.name}">
	          <label>Descripción:</label>
	          <textarea name="dishDescription" placeholder="Descripción del plato">${dish.description}</textarea>
	          <label>Precio:</label>
	          <input type="number" name="dishPrice" step="0.01" placeholder="Precio del plato (usar . para decimales)" value="${dish.price}">
	        </div>
        </c:forEach>
      </div>
      <div class="dishButton"> 
         <button type="button" id="addNewDish"><img src="${pageContext.request.contextPath}/public/add.png" alt="Añadir plato"></button>
         <button type="button" id="deleteLastDish"><img src="${pageContext.request.contextPath}/public/remove.png" alt="Eliminar plato"></button> 
      </div> 

      <div class="center">
        <input type="submit" value="Editar" >
      </div>
      
    </form>
    
  </div>

  <jsp:include page="/WEB-INF/include/footer.jsp" />

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
