package es.unex.pi.controller;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.logging.Logger;
import java.util.regex.Matcher;


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
import java.nio.charset.StandardCharsets;

import jakarta.servlet.RequestDispatcher;

import java.sql.Connection;
import java.sql.SQLException;



/**
 * Servlet implementation class ListRestaurantServlet
 */
@WebServlet("/RegisterServlet.do")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Start Register");

        
        
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
		view.forward(request,response);
		
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String firstName = request.getParameter("firstName");
	    String lastName = request.getParameter("lastName");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    
	    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);

	    // Validate parameters
	    if (firstName == null || lastName == null || email == null || password == null) {
	    	logger.info("Error parametros");
	        response.sendRedirect("WEB-INF/register.jsp");
	        return;
	    }

	    // Validate email
	    if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
	        request.setAttribute("error", "El correo electrónico no es válido");
	        logger.info("Error email");
	        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
	        view.forward(request, response);
	        return;
	    }

	    // Validate password
	    if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
	    	logger.info("password");
	        request.setAttribute("error", "La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra mayúscula, una letra minúscula y un número");
	        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
	        view.forward(request, response);
	        return;
	    }

	    // Encode password
	    String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));


	    // Create User
	    User user = new User();
	    user.setEmail(email);
	    user.setName(firstName);
	    user.setPassword(encodedPassword);
	    user.setSurname(lastName);
	    // Insert user in DB
	    userDAO.add(user); 
	    

	    // Go to login
	    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
	    view.forward(request, response);
	}

	
}
