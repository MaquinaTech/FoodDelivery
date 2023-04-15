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

/**
 * Servlet implementation class EliminarRestauranteServlet
 */

@WebServlet("/EliminarRestauranteServlet.do")
public class EliminarRestauranteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarRestauranteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/EliminarRestaurante.jsp");
		vista.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		restaurantDAO.setConnection(conn);
		Restaurant r = (Restaurant) sesion.getAttribute("restaurant");
		Long id = r.getId();
		String respuesta = request.getParameter("respuesta");
		if (respuesta.equals("Si")) {
			restaurantDAO.delete(id);
			sesion.removeAttribute("restaurant");
			response.sendRedirect(request.getContextPath() + "/LogOutServlet.do");
			    }
		else { 
			response.sendRedirect(request.getContextPath() + "/UserEditServlet.do?id=" + id);
		}
	}

}
