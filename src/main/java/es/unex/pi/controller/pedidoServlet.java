package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.pi.dao.OrderDAO;
import es.unex.pi.dao.JDBCOrderDAOImpl;
import es.unex.pi.dao.OrderDishesDAO;
import es.unex.pi.dao.JDBCOrderDishesDAOImpl;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Order;
import es.unex.pi.model.OrderDishes;
import es.unex.pi.model.Category;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.User;

/**
 * Servlet implementation class pedidoServlet
 */
public class pedidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pedidoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    Long idR  = Long.parseLong(request.getParameter("idR"));
	    List<Dish> dishes = dishDAO.getByRestaurant(idR);
	    request.setAttribute("dishes", dishes);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Pedido.jsp");
		view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
		Long idR = (Long)session.getAttribute("idrestaurante");
		List<String> listaDishes = null; 
		if (request.getParameterValues("pedido") != null) { 
			listaDishes = new ArrayList<String>(Arrays.asList(request.getParameterValues("pedido")));
			DishDAO dishDAO = new JDBCDishDAOImpl();
		    dishDAO.setConnection(conn);
		    int total = 0;		    
		    for(String d: listaDishes) {
		    	Dish dish = dishDAO.get(d);
		    	total += dish.getPrice();
		    }
		    
		    OrderDAO orderDAO = new JDBCOrderDAOImpl();
		    orderDAO.setConnection(conn);
		    Order order = new Order();
		    order.setIdu(usuario.getId());
		    order.setTotalPrice(total);
		    orderDAO.add(order);
		    OrderDishesDAO orderDishesDAO = new JDBCOrderDishesDAOImpl();
		    orderDishesDAO.setConnection(conn);
		    for(String d: listaDishes) {
		    	Dish dishes = dishDAO.get(d);
			    OrderDishes orderDishes = new OrderDishes();
			    orderDishes.setIdo(order.getId());
			    orderDishes.setIddi(dishes.getId());
			    orderDishesDAO.add(orderDishes);
		    }
		    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		    restaurantDAO.setConnection(conn);
			List<Restaurant> restaurants = restaurantDAO.getAll();
			CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		    categoryDAO.setConnection(conn);
		    List<Category> categories = categoryDAO.getAll();
			request.setAttribute("categories", categories);
			request.setAttribute("restaurants", restaurants);
		    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/list.jsp");
		    view.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/pedidoServlet.do?idR=" + idR);
		}
	}
}
