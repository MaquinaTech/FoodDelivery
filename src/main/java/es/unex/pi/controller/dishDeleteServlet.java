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

public class dishDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dishDeleteServlet() {
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
		    session.setAttribute("id", idUser);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    logger.info("LLEGAMOS ");
	    User userS = (User) session.getAttribute("user");
	    logger.info("LLEGAMOS2 ");
	    String idD = request.getParameter("idD");
	    String idR = request.getParameter("idR");
	    logger.info("PASAMO ");
	    if (userS != null) {
	        Long idUser = userS.getId();
	        session.setAttribute("id", idUser);
	    } else {
	        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
	        view.forward(request, response);
	    }
	    
	    logger.info("Id del plato: " + idD);
	    Long dishId = null;
	    if (idD != null && !idD.isEmpty()) {
	        dishId = Long.parseLong(idD);
	    }
	    
	    logger.info("Id del restaurante: "+idR);
	    Long restId = null;
	    if (idR != null) {
	    	restId = Long.parseLong(idR);
	    }
	    
	    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    dishDAO.delete(dishId);
	    response.sendRedirect(request.getContextPath() + "/restaurantDetailsServlet.do?idR=" + restId);
		
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


}

