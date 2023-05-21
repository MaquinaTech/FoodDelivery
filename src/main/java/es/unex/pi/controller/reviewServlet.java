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

import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.ReviewsDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;

/**
 * Servlet implementation class reviewServlet
 */
public class reviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idParam= Long.parseLong(request.getParameter("idR"));
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = restaurantDAO.get(idParam);
	    request.setAttribute("restaurant", restaurant);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Review.jsp");
		view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int range = Integer.parseInt(request.getParameter("review"));
	    String comentario = request.getParameter("comentario");
	    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    HttpSession session = request.getSession();
	    String email = (String) session.getAttribute("username");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    User user = userDAO.getUserByEmail(email);
	    String idParam = request.getParameter("id");
	    Long restaurantId = null;
	    if (idParam != null && !idParam.isEmpty()) {
	        restaurantId = Long.parseLong(idParam);
	    }

	    ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
	    reviewsDAO.setConnection(conn);

	    Review existingReview = reviewsDAO.get(restaurantId, user.getId());
	    if (existingReview != null) {
	        // El usuario ya ha dejado una review en este restaurante, modificar la review existente
	        existingReview.setGrade(range);
	        existingReview.setReview(comentario);
	        reviewsDAO.update(existingReview);
	    } else {
	        // El usuario no ha dejado una review en este restaurante, agregar una nueva review
	        Review newReview = new Review();
	        newReview.setIdu(user.getId());
	        newReview.setIdr(restaurantId);
	        newReview.setGrade(range);
	        newReview.setReview(comentario);
	        reviewsDAO.add(newReview);
	    }

	    // Actualizar el promedio de las calificaciones del restaurante si hay reviews existentes
	    List<Review> reviewsRest = reviewsDAO.getAllByRestaurant(restaurantId);
	    if (!reviewsRest.isEmpty()) {
	        int contador = 0;
	        for (Review r : reviewsRest) {
	            contador += r.getGrade();
	        }
	        contador /= reviewsRest.size();
	        contador = Math.round(contador);

	        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	        restaurantDAO.setConnection(conn);
	        Restaurant restaurant = restaurantDAO.get(restaurantId);
	        restaurant.setGradesAverage(contador);
	        restaurantDAO.update(restaurant);
	    }

	    response.sendRedirect(request.getContextPath() + "/restaurantDetailsServlet.do?idR=" + restaurantId);
	}

}

