package es.unex.pi.resources;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		String authHeader = request.getHeader("Authorization");
		String token= authHeader.substring("Bearer ".length()).trim();
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
							, @Context HttpServletRequest request) {
	    if(name == null || surname == null || email == null) {
	    	logger.info("ERROR");
	    	return Response.status(Response.Status.UNAUTHORIZED).entity("Error").build();
	    }
	    else {
	    	Connection conn = (Connection) sc.getAttribute("dbConn");
	    	String authHeader = request.getHeader("Authorization");
			String token= authHeader.substring("Bearer ".length()).trim();
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
	    	
	    	if(user != null) {
		    	user.setEmail(email);
		    	user.setName(name);
		    	user.setSurname(surname);
		    	userDAO.update(user);
		    	logger.info(name + " ha actualizado sus datos");
		    	return Response.ok().build();
	    	}
	    	else {
	    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
	    	}
	    }
	}
	
	@POST
	@Path("/password")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePassword (@FormParam("password1") String password1
									,@FormParam("password2") String password2
									, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
    	String authHeader = request.getHeader("Authorization");
		String token= authHeader.substring("Bearer ".length()).trim();
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
	    
	    if(password1.equals(password2)) {
	    	String passwordNew = password1; 
		    if(!passwordNew.equals("")) {
			    if (!passwordNew.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
			    	logger.info("Contraseña no válida");
			    	return Response.status(Response.Status.PRECONDITION_FAILED).entity("Error").build();
			    }
			    // Encode passwordNew
			    String encodedPasswordNew = Base64.getEncoder().encodeToString(passwordNew.getBytes(StandardCharsets.UTF_8));
			    user.setPassword(encodedPasswordNew);
			    userDAO.update(user);
			    return Response.status(Response.Status.OK).entity("OK").build();
		    }
	    }
	    else {
	    	return Response.status(Response.Status.PRECONDITION_FAILED).entity("Error").build();
	    }
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
	}
	
	@DELETE
	@Path("/deleteAccount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarCuenta(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
    	String authHeader = request.getHeader("Authorization");
		String token= authHeader.substring("Bearer ".length()).trim();
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
	    
		userDAO.delete(user.getId());
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	}
	
}
