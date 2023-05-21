package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.ReviewsDAO;
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Review;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.model.User;
import es.unex.pi.model.Dish;
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
		
		HttpSession session = request.getSession();
		User userS = (User) session.getAttribute("user");
		if (userS != null) {
		    Long idUser = userS.getId();
		    session.setAttribute("idUser", idUser);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		User user = (User) session.getAttribute("user");
		Long idUser = user.getId();
		session.setAttribute("idUser", idUser);
		String idParam= request.getParameter("idR");
		Long restaurantId = null;
	    if (idParam != null && !idParam.isEmpty()) {
	        restaurantId = Long.parseLong(idParam);
	    }
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = restaurantDAO.get(restaurantId);
	    session.setAttribute("idurestaurante", restaurant.getIdu());
	    session.setAttribute("idrestaurante", restaurant.getId());
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    List<Dish> dishes = dishDAO.getByRestaurant(restaurantId);
	    
	    
	    RestaurantCategoriesDAO resCatDAO = new JDBCRestaurantCategoriesDAOImpl();
	    resCatDAO.setConnection(conn);
	    List<RestaurantCategories> resCatList = resCatDAO.getAllByRestaurant(restaurantId);
	    
	    CategoryDAO catDAO = new JDBCCategoryDAOImpl();
	    catDAO.setConnection(conn);
	    if(resCatList != null) {
	    	Category cat = catDAO.get(resCatList.get(0).getIdct());
	    	request.setAttribute("category", cat);
	    }
	    	    
		request.setAttribute("restaurant", restaurant);		
		request.setAttribute("dishes", dishes);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/restaurantDetails.jsp");
		view.forward(request,response);
		
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	
		doGet(request, response);
	    
	}

}
