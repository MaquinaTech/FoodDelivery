package es.unex.pi.resources;

import java.util.Base64;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
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
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import es.unex.pi.model.Category;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Order;
import es.unex.pi.model.Token;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.resources.exceptions.CustomNotFoundException;

@Path("/auth")
public class LoginResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	private boolean isValidUser(String username, String password) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    User user = userDAO.get(username);    
	
	    if(user != null) {
	        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
	        if(user.getPassword().equals(encodedPassword)) {
	            return true;
	        }
	    }
	    return false;
	}

    @POST
    @Path("/token")
    public Response generateToken(User user) {
        String tokenValue = generateRandomToken();
        Date expiryDate = calculateExpiryDate();
        Token token = new Token(tokenValue, expiryDate);
        String username = user.getSurname();
	    String password = user.getPassword();
	    
	    //User auth
	    if (isValidUser(username, password)) {
	    	return Response.ok(token).build();
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("Error, usuario o contraseña no válidos").build();
	    }
    }

    private String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }
}