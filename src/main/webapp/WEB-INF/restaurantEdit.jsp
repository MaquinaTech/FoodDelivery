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
    <form action="restaurantDetails.html" method="POST">
      <img src="${pageContext.request.contextPath}/public/burguerking.png" alt="logoBurguerKing">
      <br><br>
      <h1>Datos Restaurante</h1>
      <div class="form-group">
          <label class="username" for="nombre">Nombre</label>
          <input type="text" id="nombre" name="nombre" required value="Burguer King">
      </div>
      <div class="form-group">
        <label class="username" for="direccion">Dirección</label>
        <textarea id="direccion" name="direccion" required >Calle Gomez Becerra, 1</textarea>
      </div>
      <div class="form-group">
          <label class="username" for="phone">Teléfono</label>
          <input type="tel" id="phone" name="phone" required value="654984865468" >
      </div>
      <div class="form-group">
          <label class="username" for="email">Correo electrónico</label>
          <input type="email" id="email" name="email" required value="burguerking@gmail.com">
      </div>
      <br><br>
      <h2>Rango de Precio</h2>
      <div class="input_flex">
        <div>
          <p>Mínimo:</p>
          <input type="range" id="range-min" name="range-min" min="0" max="100" value="0">
          <p id="range-value-min"></p>
        </div>
        <div>
          <p>Máximo:</p>
          <input type="range" id="range-max" name="range-max" min="0" max="100" value="100">
          <p id="range-value-max"></p>
        </div>
      </div>

      <br><br>
      <h2>Media de Valoración de los usuarios</h2>
      <div class="rating">
        <input type="radio" id="star5" name="rating" value="5">
        <label for="star5">&#9733;</label>
        <input type="radio" id="star4" name="rating" value="4" checked>
        <label for="star4">&#9733;</label>
        <input type="radio" id="star3" name="rating" value="3">
        <label for="star3">&#9733;</label>
        <input type="radio" id="star2" name="rating" value="2">
        <label for="star2">&#9733;</label>
        <input type="radio" id="star1" name="rating" value="1">
        <label for="star1">&#9733;</label>
      </div>  

      <br><br>
      <h2>Categoría</h2>
      <div class="input_flex">
          <div>
            <label class="username" for="burguer">Burguer</label>
            <input type="radio" id="burguer" name="categorias" checked >
          </div>
          <div>
          <label class="username" for="pizza">Pizza</label>
          <input type="radio" id="pizza" name="categorias" >
          </div>
          <div>
            <label class="username" for="sushi">Sushi</label>
            <input type="radio" id="sushi" name="categorias" >
          </div>
          <div>
            <label class="username" for="kebab">Kebab</label>
            <input type="radio" id="kebab" name="categorias" >
          </div>
          <div>
            <label class="username" for="italiano">Italiano</label>
            <input type="radio" id="italiano" name="categorias" >
          </div>
          <div>
            <label class="username" for="pollo">Pollo</label>
            <input type="radio" id="pollo" name="categorias" >
          </div>
      </div>

      <br><br>
      <h2>¿Bike Friendly?</h2>
      <div class="input_flex">
        <div>
          <label>SI</label>
          <input type="radio" id="bike" name="bikeFriendly">
        </div>
        <div>
          <label>NO</label>
          <input type="radio" id="noBike" name="bikeFriendly" checked>
        </div>
      </div>

      <div class="dish" id="dish">
        <div>
          <h2>Nuevo Plato</h2>
          <label>Foto:</label>
          <input type="file" name="dishPhoto">
          <br><br>
          <label>Nombre:</label>
          <input type="text" name="dishName" placeholder="Nombre del plato">
          <label>Descripción:</label>
          <textarea name="dishDescription" placeholder="Descripción del plato"></textarea>
          <label>Precio:</label>
          <input type="number" name="dishPrice" step="0.01" placeholder="Precio del plato (usar . para decimales)">
          <label>Tipo:</label>
          <select name="typeDish">
            <option value="principal">Principal</option>
            <option value="complemento">Complemento</option>
            <option value="postre">Postre</option>
            <option value="bebida">Bebida</option>
          </select>          
        </div>
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
          <label>Foto:</label>
          <input type="file" name="dishPhoto">
          <br><br>
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
