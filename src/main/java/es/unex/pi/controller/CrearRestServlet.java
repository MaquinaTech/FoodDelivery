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
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Restaurant;

/**
 * Servlet implementation class CrearRestServlet
 */
@WebServlet("/CrearRestServlet.do")
public class CrearRestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrearRestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/CrearRest.jsp");
		view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) {
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		
		String name= request.getParameter("name");
		String address= request.getParameter("address");
		String email= request.getParameter("email");
		String city= request.getParameter("city");
		String telephone= request.getParameter("telephone");
		String range_min = request.getParameter("minPrice");
		String range_max = request.getParameter("maxPrice");
		String rating= request.getParameter("gradesAverage");
		String bikeFriendly = request.getParameter("bikeFriendly");
		String available = request.getParameter("available");
		String img = request.getParameter("img");
		String subtitulo = request.getParameter("subtitulo");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = new Restaurant();
	    restaurant.setName(name);
	    restaurant.setAddress(address);
	    restaurant.setContactEmail(email);
	    restaurant.setCity(city);
	    restaurant.setTelephone(telephone);
	    Integer minPrice = Integer.parseInt(range_min);
	    Integer maxPrice = Integer.parseInt(range_max);
	    Integer gardesAverage = Integer.parseInt(rating);
	    Integer bike = 0;
	    Integer ava = 0;
	    if(bikeFriendly.equals("1")) {
	    	bike = 1;
	    }
	    if(available.equals("1")) {
	    	ava = 1;
	    }
	    
	    restaurant.setMinPrice(minPrice);
	    restaurant.setMaxPrice(maxPrice);
	    restaurant.setGradesAverage(gardesAverage);
	    restaurant.setBikeFriendly(bike);
	    restaurant.setAvailable(ava);
	    restaurant.setImg(img);
	    restaurant.setSubtitulo(subtitulo);
	    restaurant.setIdu(0);
	    restaurantDAO.add(restaurant);
	    
	    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/search.jsp");
	    view.forward(request, response);
	}

}
