package es.unex.pi.controller;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.model.User;
import es.unex.pi.util.Triplet;

import jakarta.servlet.RequestDispatcher;

import java.sql.Connection;



/**
 * Servlet implementation class ListRestaurantServlet
 */
@WebServlet("/LoginServlet.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Login");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
		view.forward(request,response);
		
	
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Inicio de sesión");
		
		String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    if (isValidUser(username, password)) {
	        HttpSession session = request.getSession(true);
	        session.setAttribute("username", username);
	        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/search.jsp");
			view.forward(request,response);
	    } else {
	    	RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp?error=1");
			view.forward(request,response);
	    }

		//RequestDispatcher view = request.getRequestDispatcher("WEB-INF/search.jsp");
		//view.forward(request,response);
		
	}
	
	private boolean isValidUser(String username, String password) {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		User user = userDAO.get(username);
		if(user != null) {
			logger.info("Login user: ");
			logger.info(username);
			if(user.getPassword().equals(password)) {
				return true;
			}
		}
		logger.info("Intento fallido: ");
		return false;
	}

	
}
