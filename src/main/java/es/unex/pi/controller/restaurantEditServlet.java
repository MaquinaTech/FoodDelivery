package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.DishDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
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
		User userS = (User) session.getAttribute("user");
		if (userS != null) {
		    Long idUser = userS.getId();
		    session.setAttribute("id", idUser);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}
		String idParam = request.getParameter("idR");
		Long restaurantId = null;
	    if (idParam != null && !idParam.isEmpty()) {
	        restaurantId = Long.parseLong(idParam);
	        request.setAttribute("idR", restaurantId);
	    }
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    Restaurant restaurant = restaurantDAO.get(restaurantId);
	    
	    CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
	    categoryDAO.setConnection(conn);
	    List<Category> categories = categoryDAO.getAll();
	    
	    RestaurantCategoriesDAO restaurantCategoryDAO = new JDBCRestaurantCategoriesDAOImpl();
	    restaurantCategoryDAO.setConnection(conn);
	    
	    List <RestaurantCategories> catRes = restaurantCategoryDAO.getAllByRestaurant(restaurantId);
	    RestaurantCategories firstCategory = null;
	    if (!catRes.isEmpty()) {
	      firstCategory = catRes.get(0);
	      Category CategoryDefault = categoryDAO.get(firstCategory.getIdct());
	      request.setAttribute("categoryName", CategoryDefault.getName());
	    }
	    
	    DishDAO dishDAO = new JDBCDishDAOImpl();
	    dishDAO.setConnection(conn);
	    List<Dish> dishes = dishDAO.getByRestaurant(restaurantId);
	    
	    session.setAttribute("restaurant", restaurant);
	    request.setAttribute("categories", categories);
	    
	    request.setAttribute("dishes", dishes);
	    
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/restaurantEdit.jsp");
		view.forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name= request.getParameter("name");
		String address= request.getParameter("address");
		String email= request.getParameter("email");
		String emailOLD= request.getParameter("emailOLD");
		String telephone= request.getParameter("telephone");
		String range_min = request.getParameter("range-min");
		String range_max = request.getParameter("range-max");
		String categories = request.getParameter("categorias");
		String bikeFriendly = request.getParameter("bikeFriendly");
		String available = request.getParameter("available");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
	    
	    CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
	    categoryDAO.setConnection(conn);
	    RestaurantCategoriesDAO restaurantCategoryDAO = new JDBCRestaurantCategoriesDAOImpl();
	    restaurantCategoryDAO.setConnection(conn);
	    Restaurant existingRestaurant = restaurantDAO.getByEmail(emailOLD);
	    
	    Category cat = categoryDAO.get(categories);
	    RestaurantCategories resCat = new RestaurantCategories();
	    resCat.setIdct(cat.getId());
	    resCat.setIdr(existingRestaurant.getId());
	    restaurantCategoryDAO.add(resCat);
	    Restaurant restaurant = new Restaurant();
	    restaurant.setName(name);
	    restaurant.setAddress(address);
	    
	    // Validate email
	    if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
	        request.setAttribute("error", "El correo electrónico no es válido");
	        logger.info("email not valid");
	        //response.sendRedirect(request.getContextPath() + "/restaurantDetails.jsp?idR=" + existingRestaurant.getId());
	        doGet(request, response);
	        return;
	    }
	    // Check if email is already in use
	    if(!email.equals(emailOLD)) {
	    	logger.info("email cambiado");
	    	logger.info(email);
	    	logger.info(emailOLD);
		    if (existingRestaurant != null && existingRestaurant.getContactEmail() != email ) {
		    	logger.info("email igual");
		        request.setAttribute("error", "El correo electrónico ya está registrado");
		        request.setAttribute("idR", existingRestaurant.getId());
		        //response.sendRedirect(request.getContextPath() + "/restaurantDetails.jsp?idR=" + existingRestaurant.getId());
		        doGet(request, response);
		    }
	    }
	    restaurant.setContactEmail(email);
	    restaurant.setTelephone(telephone);
	    Integer minPrice = Integer.parseInt(range_min);
	    Integer maxPrice = Integer.parseInt(range_max);
	    Integer bike = 0;
	    if(bikeFriendly.equals("1")) {
	    	bike = 1;
	    }
	    else {
	    	bike = 0;
	    }
	    Integer ava = 0;
	    if(available.equals("1")) {
	    	ava = 1;
	    }
	    else {
	    	ava = 0;
	    }
	    
	    restaurant.setMinPrice(minPrice);
	    restaurant.setMaxPrice(maxPrice);
	    restaurant.setBikeFriendly(bike);
	    HttpSession session = request.getSession();
	    Integer idU = (int) (long) session.getAttribute("id");
	    restaurant.setIdu(idU);
	    session.setAttribute("id", idU);
	    restaurant.setAvailable(ava);
	    Restaurant newRestaurant = restaurantDAO.getByEmail(emailOLD);
	    restaurant.setId(newRestaurant.getId());
	    restaurantDAO.update(restaurant);

	    // Go to edit
	    response.sendRedirect(request.getContextPath() + "/restaurantDetailsServlet.do?idR=" + restaurant.getId());
	}

	
}
