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
import java.util.ArrayList;
import java.util.List;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Restaurant;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;


/**
 * Servlet implementation class BusquedaEstadosServlet
 */
public class BusquedaEstadosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusquedaEstadosServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/list.jsp");
		vista.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String busqueda = request.getParameter("estado");
			Connection conn = (Connection) getServletContext().getAttribute("dbConn");
			RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
			restaurantDAO.setConnection(conn);
			List<Restaurant> restaurants = restaurantDAO.getAll();
			if (busqueda.equals("noacepta")) {
				for (Restaurant rest : restaurants) {
					if (rest.getAvailable() == 0) {
						restaurants.add(rest);
					}
				}
				request.setAttribute("noacepta", "checked");
			} else if (busqueda.equals("acepta")) {
				for (Restaurant rest : restaurants) {
					if (rest.getAvailable() == 1) {
						restaurants.add(rest);
					}
				}
				request.setAttribute("acepta", "checked");
			}
			request.setAttribute("restaurants", restaurants);
			HttpSession sesion = request.getSession();
			if (sesion.getAttribute("usuario") != null) {
				request.setAttribute("sesion", "sesionIniciada");
			}
			RequestDispatcher vista = request.getRequestDispatcher("/WEB-INF/BusquedaEstados.jsp");
			vista.forward(request, response);
		}
	}
