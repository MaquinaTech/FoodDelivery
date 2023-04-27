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

import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.ReviewsDAO;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;

/**
 * Servlet implementation class reviewServlet
 */
public class reviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		int range= Integer.parseInt(request.getParameter("range"));
		String comentario= request.getParameter("comentario");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		User usuario = (User) sesion.getAttribute("user");
		String idParam= request.getParameter("id");
		Long restaurantId = null;
	    if (idParam != null && !idParam.isEmpty()) {
	        restaurantId = Long.parseLong(idParam);
	    }
	    if(usuario.getId() == restaurantId) {
			String error = "El dueño del restaurante no puede crear reviews del mismo.";
			request.setAttribute("error", error);
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/restaurantDetails.jsp");
			view.forward(request,response);
	    }
	    else { 
		    ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
		    reviewsDAO.setConnection(conn);
		    Review review = new Review();
		    review.setIdu(usuario.getId());
		    review.setIdr(restaurantId);
		    review.setGrade(range);
		    review.setReview(comentario);
		    reviewsDAO.add(review);
		    
		    List<Review> reviewsRest = reviewsDAO.getAllByRestaurant(restaurantId);
		    if(reviewsRest.size() != 0) {
		    	int contador = 0;
		    	for(Review r: reviewsRest) {
		    		contador+= r.getGrade();
		    	}
		    	if(contador != 0) {
		    		contador/= reviewsRest.size();
		    		contador = Math.round(contador);
		    		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		    	    restaurantDAO.setConnection(conn);
		    	    Restaurant restaurant = restaurantDAO.get(restaurantId);
		    	    restaurant.setGradesAverage(contador);
		    	    restaurantDAO.update(restaurant);
		    	}
		    }
		    response.sendRedirect(request.getContextPath() + "/restaurantDetailsServlet.do?idR=" + restaurantId);
	    }
	}
}

