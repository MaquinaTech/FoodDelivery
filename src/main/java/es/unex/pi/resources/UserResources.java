package es.unex.pi.resources;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/users")

public class UserResources {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserSesion(@Context HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		User usuario = (User) sesion.getAttribute("usuario");
		if (usuario != null) {
			return usuario;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@PUT
	@Path("/UserEdit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response UserEdit(User user, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		Connection conn = (Connection) sc.getAttribute("dbConn");		
		UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);    

	    // Validate parameters
	    if (user.getName() == null || user.getSurname() == null || user.getEmail() == null) {
	    	throw new WebApplicationException(Response.Status.BAD_REQUEST);
	    }

	    // Validate email
	    if (!user.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
	    	throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
	    }
	    
	    if(!user.getPassword().equals("")) {
		    if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
		    	throw new WebApplicationException(Response.Status.CONFLICT);
		    }
	    }
	    
	    session.removeAttribute("username");
	    userDAO.update(user);
		session.setAttribute("username", user);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
	@DELETE
	@Path("/eliminarCuenta")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarCuenta(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		Long id = (Long) sesion.getAttribute("id");
		userDAO.delete(id);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
}
