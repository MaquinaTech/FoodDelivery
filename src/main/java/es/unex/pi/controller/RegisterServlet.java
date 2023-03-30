package es.unex.pi.controller;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
	
	// Validate user email
    private static final String EMAIL_PATTERN = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    // Validate user password
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

    
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
		logger.info("Register");

        
        
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
		view.forward(request,response);
		
	
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		String username = request.getParameter("username");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        logger.info("Parametros: ");
        logger.info(username);
        logger.info(lastName);
        logger.info(email);
        logger.info(password);

        // Validar que se ingresen los campos requeridos
        if (username == null || lastName == null || email == null || password == null || username.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
        	logger.info("Error en parametros");
            request.setAttribute("errorMessage", "Porfavor, rellene todos los campos requeridos");
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
            view.forward(request, response);
            return;
        }
        
        // Validate email pattern
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!emailMatcher.matches()) {
        	logger.info("Error email");
            request.setAttribute("errorMessage", "Please enter a valid email address");
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
            view.forward(request, response);
            return;
        }

        // Validate password is strong
        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!passwordMatcher.matches()) {
        	logger.info("Error contraseña");
            request.setAttribute("errorMessage", "La contraseña debe tener, al menor, 8 caracteres, además de incluir una mayúscula, una minúscula y un número");
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
            view.forward(request, response);
            return;
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();
            String passwordString = new String(hashedPassword, StandardCharsets.UTF_8);
            // Insertar los datos del usuario en la base de datos
            User user = new User();
            user.setName(username);
            user.setSurname(lastName);
            user.setEmail(email);
            user.setPassword(passwordString);
            userDAO.add(user);

            // Redirigir al usuario a la página de inicio de sesión
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
            view.forward(request, response);
        } catch (NoSuchAlgorithmException e) {
        	logger.info("Error inserción");
            throw new ServletException("Error inserting user data into database", e);
        }
	    
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/register.jsp");
		view.forward(request,response);

		//RequestDispatcher view = request.getRequestDispatcher("WEB-INF/search.jsp");
		//view.forward(request,response);
		
	}

	
}
