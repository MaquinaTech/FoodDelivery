package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCTokenDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.model.Token;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.TokenDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/restaurants")
		
public class RestaurantResources {
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> getRestaurants(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		return restaurantDAO.getAll();
	}
	
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    CategoryDAO CategoryDAO = new JDBCCategoryDAOImpl();
	    CategoryDAO.setConnection(conn);
	    List<Category> categoryList = CategoryDAO.getAll();
	    
	    if(categoryList.size() > 0) {
	    	return categoryList;
	    }
	    else {
	    	throw new WebApplicationException(Response.Status.NOT_FOUND);
	    }
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object[] obtenerRestauranteDetalles(@FormParam("idR") long idR, @Context HttpServletRequest request) {
	    Connection conn = (Connection) sc.getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = restaurantDAO.get(idR);

	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    List<Dish> dishes = dishDAO.getByRestaurant(idR);

	    RestaurantCategoriesDAO resCatDAO = new JDBCRestaurantCategoriesDAOImpl();
	    resCatDAO.setConnection(conn);
	    List<RestaurantCategories> resCatList = resCatDAO.getAllByRestaurant(idR);
	    
	    CategoryDAO CategoryDAO = new JDBCCategoryDAOImpl();
	    CategoryDAO.setConnection(conn);
	    
	    List<Category> categoryList = new ArrayList<>();
	    
	    for (RestaurantCategories restaurantCategories : resCatList) {
	        long categoryId = restaurantCategories.getIdct();
	        Category category = CategoryDAO.get(categoryId);
	        if (category != null) {
	            categoryList.add(category);
	        }
	    }
	    
	    String authHeader = request.getHeader("Authorization");
		String token= authHeader.substring("Bearer ".length()).trim();
    	TokenDAO tokenDAO = new JDBCTokenDAOImpl();
	    tokenDAO.setConnection(conn);
	    List<Token> listTokens = tokenDAO.getAll();
	    long idU = -1;
	    for (Token t : listTokens) {
	        if (t.getValue().equals(token)) {
	            idU = t.getIdU();
	            break;
	        }
	    }
	    
	    boolean owner = false;
	    if(idU == restaurant.getIdu()) {
	    	owner = true;
	    }

	    // Estructurar los datos en un array y devolverlo
	    Object[] result = new Object[4];
	    result[0] = restaurant;
	    result[1] = dishes;
	    result[2] = categoryList;
	    result[3] = owner;
	    if(result.length == 4) {
	    	return result;
	    }
	    else {
	    	throw new WebApplicationException(Response.Status.BAD_REQUEST);
	    }
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRestaurant(@FormParam("name") String name
			,@FormParam("address") String address
			,@FormParam("telephone") String telephone
			,@FormParam("city") String city
			,@FormParam("minPrice") Integer minPrice
			,@FormParam("maxPrice") Integer maxPrice
			,@FormParam("bikeFriendly") Integer bikeFriendly
			,@FormParam("available") Integer available
			,@FormParam("contactEmail") String contactEmail
			,@FormParam("category") Integer category
			,@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		String authHeader = request.getHeader("Authorization");
		String token= authHeader.substring("Bearer ".length()).trim();
    	TokenDAO tokenDAO = new JDBCTokenDAOImpl();
	    tokenDAO.setConnection(conn);
	    List<Token> listTokens = tokenDAO.getAll();
	    long idU = -1;
	    for (Token t : listTokens) {
	        if (t.getValue().equals(token)) {
	            idU = t.getIdU();
	            break;
	        }
	    }
		
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = new Restaurant();
	    restaurant.setIdu((int)idU);
	    restaurant.setName(name);
		restaurant.setAddress(address);
		restaurant.setTelephone(telephone);
		restaurant.setCity(city);
		restaurant.setMinPrice(minPrice);
		restaurant.setMaxPrice(maxPrice);
		restaurant.setBikeFriendly(bikeFriendly);
		restaurant.setAvailable(available);
		restaurant.setContactEmail(contactEmail);
		restaurantDAO.add(restaurant);
		
		RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
		restaurantCategoriesDAO.setConnection(conn);
		RestaurantCategories restCat = new RestaurantCategories();
		restCat.setIdct(category);
		
		List<Restaurant> listRest = restaurantDAO.getAll();
		restCat.setIdr(listRest.get(listRest.size()-1).getId());
		restaurantCategoriesDAO.add(restCat);
			
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRestaurant(@FormParam("id") long id
							,@FormParam("name") String name
							,@FormParam("address") String address
							,@FormParam("telephone") String telephone
							,@FormParam("city") String city
							,@FormParam("minPrice") Integer minPrice
							,@FormParam("maxPrice") Integer maxPrice
							,@FormParam("bikeFriendly") Integer bikeFriendly
							,@FormParam("available") Integer available
							,@FormParam("contactEmail") String contactEmail
							, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(id);
		restaurant.setName(name);
		restaurant.setAddress(address);
		restaurant.setTelephone(telephone);
		restaurant.setCity(city);
		restaurant.setMinPrice(minPrice);
		restaurant.setMaxPrice(maxPrice);
		restaurant.setBikeFriendly(bikeFriendly);
		restaurant.setAvailable(available);
		restaurant.setContactEmail(contactEmail);
		String authHeader = request.getHeader("Authorization");
		String token= authHeader.substring("Bearer ".length()).trim();
    	TokenDAO tokenDAO = new JDBCTokenDAOImpl();
	    tokenDAO.setConnection(conn);
	    List<Token> listTokens = tokenDAO.getAll();
	    long idU = -1;
	    for (Token t : listTokens) {
	        if (t.getValue().equals(token)) {
	            idU = t.getIdU();
	            break;
	        }
	    }
		restaurant.setIdu((int) idU);
		restaurantDAO.update(restaurant);
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/dish/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDish(@FormParam("id") long id
							,@FormParam("description") String description
							,@FormParam("name") String name
							,@FormParam("price") Integer price
							, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl();
		dishDAO.setConnection(conn);
		Dish dish = dishDAO.get(id);
		dish.setDescription(description);
		dish.setName(name);
		dish.setPrice(price);
		logger.info("Actualizando Dish....");
		logger.info(""+dish.getId());
		logger.info(""+dish.getIdr());
		dishDAO.update(dish);
		
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@POST
	@Path("/dish/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Long addDish(@FormParam("idR") long idR
							,@FormParam("description") String description
							,@FormParam("name") String name
							,@FormParam("price") Integer price
							, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl();
		dishDAO.setConnection(conn);
		Dish dish = new Dish();
		dish.setDescription(description);
		dish.setName(name);
		dish.setPrice(price);
		dish.setIdr(idR);
		logger.info(name);
		logger.info(description);
		dishDAO.add(dish);
		Dish newDish = dishDAO.get(name);
		Long id = newDish.getId();
		//String errorListJson = "{\"error\": false, \"data\": \"No hay datos disponibles.\"}";
        //return Response.ok(errorJson).build();
		return id;
        
	}
	
	@DELETE
	@Path("/dish/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Integer deleteDish(@FormParam("id") Integer id, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl();
		dishDAO.setConnection(conn);
		dishDAO.delete(id);
        return id;
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRest(@FormParam("idR") Integer idR, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		restaurantDAO.delete(idR);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}


}

