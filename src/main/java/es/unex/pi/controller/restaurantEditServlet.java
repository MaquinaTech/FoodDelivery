package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
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
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/restaurantEditServlet.do")
public class restaurantEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public restaurantEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session != null) {
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		String idParam= request.getParameter("idR");
		Long restaurantId = null;
	    if (idParam != null && !idParam.isEmpty()) {
	        restaurantId = Long.parseLong(idParam);
	    }
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = restaurantDAO.get(restaurantId);
	    
	    CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
	    categoryDAO.setConnection(conn);
	    List<Category> categories = categoryDAO.getAll();
	    
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    List<Dish> dishes = dishDAO.getByRestaurant(restaurantId);
	    
	    request.setAttribute("restaurant", restaurant);
	    request.setAttribute("categories", categories);
	    request.setAttribute("dishes", dishes);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/restaurantEdit.jsp");
		view.forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String idParam= request.getParameter("idR");
		String idParam= request.getParameter("idR");
		String idParam= request.getParameter("idR");
		String idParam= request.getParameter("idR");
		String idParam= request.getParameter("idR");
		String idParam= request.getParameter("idR");
		String idParam= request.getParameter("idR");*/
	    

	    // Go to login
	    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/search.jsp");
	    view.forward(request, response);
	}

	
}
