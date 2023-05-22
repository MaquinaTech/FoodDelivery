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
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.ReviewsDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;

/**
 * Servlet implementation class reviewServlet
 */

public class dishServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dishServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userS = (User) session.getAttribute("user");
		if (userS != null) {
		    Long idUser = userS.getId();
		    session.setAttribute("idUser", idUser);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		Long idR = Long.parseLong(request.getParameter("idR"));
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    List<Dish> dishes = dishDAO.getByRestaurant(idR);
	    request.setAttribute("dishes", dishes);
	    request.setAttribute("idR", idR);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/editDish.jsp");
		view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userS = (User) session.getAttribute("user");
		if (userS != null) {
		    Long idUser = userS.getId();
		    session.setAttribute("idUser", idUser);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		String name = request.getParameter("dishName");
		String description = request.getParameter("dishDescription");
		String price = request.getParameter("dishPrice");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    Dish newDish = new Dish();
	    newDish.setName(name);
	    String idParam= request.getParameter("idR");
		Long idR = null;
	    if (idParam != null) {
	        idR = Long.parseLong(idParam);
	    }
	    newDish.setIdr(idR);
	    newDish.setDescription(description);
	    newDish.setPrice(Integer.parseInt(price));
	    dishDAO.add(newDish);
	    response.sendRedirect(request.getContextPath() + "/restaurantDetailsServlet.do?idR=" + idR);
		
	}

}

