package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
	@Path("/{idR}")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant obtenerRestauranteDetalles(@PathParam("idR") long idR, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		return restaurantDAO.get(idR);
	}
	
	@GET
	@Path("searchAdress/{Adress}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> getRestaurantPorAdress(@PathParam("Adress") String Adress,
			@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
	    categoryDAO.setConnection(conn);
	    RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
	    restaurantCategoriesDAO.setConnection(conn);
		List<Restaurant> filterRestaurantsAddress = null;
		filterRestaurantsAddress = new ArrayList<Restaurant>();
		filterRestaurantsAddress = restaurantDAO.getAllBySearchAddress(Adress);
		return filterRestaurantsAddress;
	}
	
	@GET
	@Path("searchCategory/{Category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> getRestaurantPorCategory(@PathParam("Category") String Category,
			@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    List<Restaurant> restaurants = restaurantDAO.getAll();
	    CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
	    categoryDAO.setConnection(conn);
	    RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
	    restaurantCategoriesDAO.setConnection(conn);
		List<Restaurant> filterRestaurantsCategory = null;
		filterRestaurantsCategory = new ArrayList<Restaurant>();
		Category cat = categoryDAO.get(Category);
		List<Long> RestCat = restaurantCategoriesDAO.getAllIdsByCategory(cat.getId());
		Iterator<Restaurant> itRestaurantList = restaurants.iterator();
		while(itRestaurantList.hasNext()) {
			Restaurant it = (Restaurant) itRestaurantList.next();
			if(RestCat.contains(it.getId())) {
				filterRestaurantsCategory.add((it) );
			}
		}
		return filterRestaurantsCategory;
	}
	
	@GET
	@Path("estado/{estado}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> getRestaurantPorEstado(@PathParam("estado") String estado,
			@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		List<Restaurant> restaurantsAll = restaurantDAO.getAll();
		List<Restaurant> restaurants = new ArrayList<>();
		if (estado.equals("noacepta")) {
			for (Restaurant rest : restaurantsAll) {
				if (rest.getAvailable() == 0) {
					restaurants.add(rest);
				}
			}
			//request.setAttribute("noacepta", "checked");
		} else if (estado.equals("acepta")) {
			for (Restaurant rest : restaurantsAll) {
				if (rest.getAvailable() == 1) {
					restaurants.add(rest);
				}
			}
			//request.setAttribute("acepta", "checked");
		}
		return restaurants;
	}
	
	@POST
	@Path("/crearRest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearRestaurante(Restaurant restaurant, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		User usuario = (User) sesion.getAttribute("user");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    restaurant.setIdu((int)usuario.getId());
		List<String> listaCategorias = null; 
		if (request.getParameterValues("categorias") != null) { 
			listaCategorias = new ArrayList<String>(Arrays.asList(request.getParameterValues("categorias")));
		    CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		    categoryDAO.setConnection(conn);
		    RestaurantCategoriesDAO restaurantCatDAO = new JDBCRestaurantCategoriesDAOImpl();
		    restaurantCatDAO.setConnection(conn);
		    Long idR = restaurantDAO.add(restaurant);
		    for (String categoria : listaCategorias) {
				Category cat = categoryDAO.get(categoria);
				RestaurantCategories restaurantCat = new RestaurantCategories();
				restaurantCat.setIdr(idR);
				restaurantCat.setIdct(cat.getId());
				restaurantCatDAO.add(restaurantCat);
			}
			return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
		}
		else {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}
	
	@PUT
	@Path("/editarRest/{idR}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarRest( Restaurant restaurant, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    restaurantDAO.update(restaurant);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	
	@DELETE
	@Path("/eliminarRestaurante/{idR}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrarRest(@PathParam("idR") long idR, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		restaurantDAO.delete(idR);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}


}

