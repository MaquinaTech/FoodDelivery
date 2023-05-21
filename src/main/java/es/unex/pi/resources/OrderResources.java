package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.JDBCTokenDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.model.Review;
import es.unex.pi.model.Token;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.ReviewsDAO;
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
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviews(@Context HttpServletRequest request) {
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
	    
	    OrderDAO orderDAO = new JDBCOrderDAOImpl();
	    orderDAO.setConnection(conn);
	    List<Order> listaOrders = orderDAO.getAll(); 

	    List<Order> listaOrdersUser = new ArrayList<Order>(); 
	    for (Order order : listaOrders) {
	        if(order.getIdu() == idU) {
	        	listaOrdersUser.add(order); 
	        }
	    }
	    OrderDishesDAO orderDishesDAO = new JDBCOrderDishesDAOImpl();
	    orderDishesDAO.setConnection(conn);
	    
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    
	    List<OrderDishes> listaOrdersConPlato = new ArrayList<>();
	    
	 // Creamos un objeto Map para almacenar los datos
	    Map<Order, List<Dish>> orderMap = new HashMap<>();
	    // Recorremos los orders del usuario
	    for (Order order : listaOrdersUser) {
	        List<OrderDishes> orderDishes = orderDishesDAO.getAllByOrder(order.getId());
	        List<Dish> dishes = new ArrayList<>();
	        for (OrderDishes orderD : orderDishes) {
	            Dish newDish = dishDAO.get(orderD.getIddi());
	            dishes.add(newDish);
	        }
	        // Añadimos al map la order junto con su lista de platos
	        orderMap.put(order, dishes);
	    }

	    
	    // Devuelve la lista de pedidos del usuario con el nombre del plato correspondiente en formato JSON
	    return Response.ok(orderMap).build();
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeOrder(List<Dish> dishes, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    int total = 0;		    
	    for(Dish d: dishes) {
	    	total += d.getPrice();
	    }	    
	    OrderDAO orderDAO = new JDBCOrderDAOImpl();
	    orderDAO.setConnection(conn);
	    
	    
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
	    
	    Order order = new Order();
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
	
}
