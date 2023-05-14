package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.JDBCOrderDAOImpl;
import es.unex.pi.dao.OrderDishesDAO;
import es.unex.pi.dao.JDBCOrderDishesDAOImpl;
import es.unex.pi.model.Order;
import es.unex.pi.model.OrderDishes;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCTokenDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.model.Token;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.TokenDAO;
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

@Path("/orders")
public class OrderResources {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order obtenerOrder(@PathParam("id") long id, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		OrderDAO orderDAO = new JDBCOrderDAOImpl();
		orderDAO.setConnection(conn);
		return orderDAO.get(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeOrder(List<Dish> dishes, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		logger.info("Hemos llegao");
		DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    int total = 0;		    
	    for(Dish d: dishes) {
	    	total += d.getPrice();
	    }	    
	    OrderDAO orderDAO = new JDBCOrderDAOImpl();
	    orderDAO.setConnection(conn);
	    Order order = new Order();
	    
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
	    
	    order.setIdu(idU);
	    order.setTotalPrice(total);
	    orderDAO.add(order);
	    
	    order = orderDAO.get(idU, total);
	    
	    OrderDishesDAO orderDishesDAO = new JDBCOrderDishesDAOImpl();
	    orderDishesDAO.setConnection(conn);
	    for(Dish d: dishes) {
		    OrderDishes orderDishes = new OrderDishes();
		    orderDishes.setIdo(order.getId());
		    orderDishes.setIddi(d.getId());
		    orderDishesDAO.add(orderDishes);
	    }
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();

	}
	
	/*@POST
	@Path("/crearOrder/{Dishes}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearOrder(Order order, @PathParam("Dishes") List <Dish> Dishes, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
		DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    int total = 0;		    
	    for(Dish d: Dishes) {
	    	total += d.getPrice();
	    }	    
	    OrderDAO orderDAO = new JDBCOrderDAOImpl();
	    orderDAO.setConnection(conn);
	    order.setIdu(usuario.getId());
	    order.setTotalPrice(total);
	    orderDAO.add(order);
	    OrderDishesDAO orderDishesDAO = new JDBCOrderDishesDAOImpl();
	    orderDishesDAO.setConnection(conn);
	    for(Dish d: Dishes) {
		    OrderDishes orderDishes = new OrderDishes();
		    orderDishes.setIdo(order.getId());
		    orderDishes.setIddi(d.getId());
		    orderDishesDAO.add(orderDishes);
	    }
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();

	}*/
}
