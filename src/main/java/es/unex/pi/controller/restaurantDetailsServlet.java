package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/restaurantDetailsServlet.do")
public class restaurantDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public restaurantDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Get Restaurants Details");
		long id = Long.parseLong(request.getParameter("id"));
		
		HttpSession session = request.getSession(false);
		if (session != null) {
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = restaurantDAO.get(id);
	    
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    
	    
		request.setAttribute("restaurant", restaurant);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/restaurantDetails.jsp");
		view.forward(request,response);
		
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	    

	    // Go to login
	    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/search.jsp");
	    view.forward(request, response);
	}

	
}
