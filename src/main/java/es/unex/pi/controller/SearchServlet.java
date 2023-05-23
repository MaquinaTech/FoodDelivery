package es.unex.pi.controller;

import java.io.IOException;
import java.util.*;
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
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import jakarta.servlet.RequestDispatcher;
import java.sql.Connection;




/**
 * Servlet implementation class ListRestaurantServlet
 */
@WebServlet("/SearchServlet.do")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Get Restaurants");
		
		HttpSession session = request.getSession();
		if (session != null) {
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    List<Restaurant> restaurants = restaurantDAO.getAll();
	    CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
	    categoryDAO.setConnection(conn);
	    List<Category> categories = categoryDAO.getAll();
	    RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
	    restaurantCategoriesDAO.setConnection(conn);
	    
	    
	    String address = request.getParameter("address");
		String category = request.getParameter("category");
			
		//Filter category
		List<Restaurant> filterRestaurantsCategory = null;
		if(category != null) {
			filterRestaurantsCategory = new ArrayList<Restaurant>();
			Category cat = categoryDAO.get(category);
			List<Long> RestCat = restaurantCategoriesDAO.getAllIdsByCategory(cat.getId());
			Iterator<Restaurant> itRestaurantList = restaurants.iterator();
			while(itRestaurantList.hasNext()) {
				Restaurant it = (Restaurant) itRestaurantList.next();
				if(RestCat.contains(it.getId())) {
					filterRestaurantsCategory.add((it) );
				}
			}
		}
		
		
		//Filter address
		List<Restaurant> filterRestaurantsAddressCat = null;
		if(address != null) {
			filterRestaurantsAddressCat = new ArrayList<Restaurant>();
			filterRestaurantsAddressCat = restaurantDAO.getAllBySearchAddress(address);
			if(category != null) {
				Iterator<Restaurant> itRestaurantListAddress = filterRestaurantsCategory.iterator();
				while(itRestaurantListAddress.hasNext()) {
					Restaurant it = (Restaurant) itRestaurantListAddress.next();
					if(!filterRestaurantsAddressCat.contains(it)) {
						filterRestaurantsAddressCat.remove(it);
					}					
				}
			}
		}
		
	
		//Set attributes to request
	    if(filterRestaurantsAddressCat !=  null) {
	    	request.setAttribute("restaurants", filterRestaurantsAddressCat);
	    }
	    else if(filterRestaurantsCategory !=  null) {
	    	request.setAttribute("restaurants", filterRestaurantsCategory);
	    }
	    else {
	    	request.setAttribute("restaurants", restaurants);
	    }
		
		//Set categories to request
		request.setAttribute("categories", categories);
		request.setAttribute("all", "checked");
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/list.jsp");
		view.forward(request,response);
		
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String busqueda = request.getParameter("estado");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
	    categoryDAO.setConnection(conn);
	    List<Category> categories = categoryDAO.getAll();
	    RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
	    restaurantCategoriesDAO.setConnection(conn);
		List<Restaurant> restaurantsAll = restaurantDAO.getAll();
		List<Restaurant> restaurants = new ArrayList<>();
		if (busqueda.equals("noacepta")) {
			for (Restaurant rest : restaurantsAll) {
				if (rest.getAvailable() == 0) {
					restaurants.add(rest);
				}
			}
			request.setAttribute("noacepta", "checked");
		} else if (busqueda.equals("acepta")) {
			for (Restaurant rest : restaurantsAll) {
				if (rest.getAvailable() == 1) {
					restaurants.add(rest);
				}
			}
			request.setAttribute("acepta", "checked");
		} else {
			for (Restaurant rest : restaurantsAll) {
				restaurants.add(rest);
			}
			request.setAttribute("all", "checked");
			
		}
		request.setAttribute("restaurants", restaurants);
		HttpSession sesion = request.getSession();
		if (sesion.getAttribute("usuario") != null) {
			request.setAttribute("sesion", "sesionIniciada");
		}
		request.setAttribute("categories", categories);
		RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/list.jsp");
		vista.forward(request, response);
	}

	
}
