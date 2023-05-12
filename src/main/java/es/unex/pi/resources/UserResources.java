package es.unex.pi.resources;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCTokenDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.TokenDAO;
import es.unex.pi.model.Token;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
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
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@FormParam("token") String token, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    
	    TokenDAO tokenDAO = new JDBCTokenDAOImpl();
	    tokenDAO.setConnection(conn);
	    List<Token> listTokens = tokenDAO.getAll();
	    long idU = -1;
	    
	    for (Token t : listTokens) {
	        if (t.getValue().equals(token)) {
	            idU = t.getIdU();
	            break;
	        }
	    }
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    User user = userDAO.get(idU);
	    User userFilter = new User();
	    userFilter.setEmail(user.getEmail());
	    userFilter.setId(user.getId());
	    userFilter.setName(user.getName());
	    userFilter.setSurname(user.getSurname());
		
		if (user != null) {
			return userFilter;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@FormParam("name") String name
							,@FormParam("surname") String surname
							,@FormParam("email") String email
							,@FormParam("id") String id
							, @Context HttpServletRequest request) {
		logger.info("Hemos llegao chavales");
	    if(name == null || surname == null || email == null) {
	    	return Response.status(Response.Status.UNAUTHORIZED).entity("Error").build();
	    }
	    else {
	    	Connection conn = (Connection) sc.getAttribute("dbConn");
	    	UserDAO userDAO = new JDBCUserDAOImpl();
	    	User user = userDAO.get(id);
	    	
	    	return Response.ok().build();
	    }
		
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
