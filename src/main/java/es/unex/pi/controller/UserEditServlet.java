package es.unex.pi.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.model.Dish;
import es.unex.pi.model.User;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UserEditServlet.do")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) {
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		String idParam= request.getParameter("id");
		Long userId = null;
	    if (idParam != null && !idParam.isEmpty()) {
	        userId = Long.parseLong(idParam);
	    }
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    User user = userDAO.get(userId);
	    
	    request.setAttribute("user", user);

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/UserEdit.jsp");
		view.forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name= request.getParameter("name");
		String surname= request.getParameter("surname");
		String email= request.getParameter("email");
		String userId= request.getParameter("userId");
		Long id = Long.parseLong(userId);
		String passwordNew= request.getParameter("passwordNew");
		logger.info("----------------------");
		logger.info(name);
		logger.info(surname);
		logger.info(email);
		logger.info(passwordNew);
		logger.info("----------------------");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    
	    // Validate parameters
	    if (name == null || surname == null || email == null || passwordNew == null) {
	    	logger.info("Error parametros");
	        response.sendRedirect("WEB-INF/UserEdit.jsp");
	        return;
	    }

	    // Validate email
	    if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
	        request.setAttribute("error", "El correo electrónico no es válido");
	        logger.info("Error email");
	        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/UserEdit.jsp");
	        view.forward(request, response);
	        return;
	    }
	    
	    User user = new User();
	    
	    if(passwordNew.isEmpty()) {
	    	// Validate password
		    if (!passwordNew.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
		    	logger.info("password");
		    	logger.info(passwordNew);
		        request.setAttribute("error", "La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra mayúscula, una letra minúscula y un número");
		        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/UserEdit.jsp?id=" + userId);
		        view.forward(request, response);
		        return;
		    }
		    // Encode passwordNew
		    String encodedPasswordNew = Base64.getEncoder().encodeToString(passwordNew.getBytes(StandardCharsets.UTF_8));
		    user.setPassword(encodedPasswordNew);
	    }
	    user.setName(name);
	    user.setSurname(surname);
	    user.setEmail(email);
	    userDAO.update(user);
	    
	    // Go to edit
	    request.setAttribute("id", user.getId());
	    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/UserEdit.jsp?id=" + userId);
	    view.forward(request, response);
	  
	}

	
}
