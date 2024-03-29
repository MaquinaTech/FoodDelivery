package es.unex.pi.resources;
import java.util.logging.Logger;
import java.util.Base64;
import java.sql.Connection;
import java.util.List;
import java.sql.Date;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.FormParam;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.JDBCTokenDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.TokenDAO;
import es.unex.pi.model.User;
import es.unex.pi.model.Token;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.resources.exceptions.CustomNotFoundException;

@Path("/auth")
public class LoginResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	private boolean isValidUser(User user, String password) {
		
	    if(user != null) {
	        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
	        if(user.getPassword().equals(encodedPassword)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("email") String email,
							  @FormParam("name") String name,
							  @FormParam("surname") String surname,
                              @FormParam("password") String password,
                              @Context HttpServletRequest request) {
    	Connection conn = (Connection) sc.getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    User user = new User();
	    user.setEmail(email);
	    user.setName(name);
	    user.setSurname(surname);
	    if(!password.equals("")) {
		    if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
		    	logger.info("Contraseña no válida");
		    	String errorJson = "{\"error\": true, \"message\": \"Contraseña no válida\"}";
		        return Response.ok(errorJson).build();
		    }
	    }
	    String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
	    user.setPassword(encodedPassword);
	    
	    List <User> listUsers = userDAO.getAll();
	    for (User u : listUsers) {
	    	if(u.getEmail().equals(email)) {
	    		logger.info("HOOOOOOOLAAA: ");
	    		logger.info(email);
	    		String errorJson = "{\"error\": true, \"message\": \"El correo ya existe\"}";
		        return Response.ok(errorJson).build();
	    	}
	    }
	    userDAO.add(user);
	   
	    String errorJson = "{\"error\": false, \"message\": \"El correo ya existe\"}";
        return Response.ok(errorJson).build();
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateToken(@FormParam("email") String email,
	                              @FormParam("password") String password,
	                              @Context HttpServletRequest request) {
    	Connection conn = (Connection) sc.getAttribute("dbConn");
	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn);
	    User user = userDAO.find(email);
	    
	    //User auth
	    if (isValidUser(user,password )) {
	    	String tokenValue = generateRandomToken();
	        Date expiryDate = calculateExpiryDate();
	        Token token = new Token(tokenValue, expiryDate, user.getId());
	        TokenDAO tokenDAO = new JDBCTokenDAOImpl();
		    tokenDAO.setConnection(conn);
		    tokenDAO.add(token);
	    	return Response.ok(token).build();
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("Error, usuario o contraseña no válidos").build();
	    }
    }
	
	@POST
	@Path("/verify")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyToken(@Context HttpServletRequest request) {
	    Connection conn = (Connection) sc.getAttribute("dbConn");
	    String authHeader = request.getHeader("Authorization");
		String token= authHeader.substring("Bearer ".length()).trim();
		
	    TokenDAO tokenDAO = new JDBCTokenDAOImpl();
	    tokenDAO.setConnection(conn);
	    List<Token> listTokens = tokenDAO.getAll();
	    boolean verify = false;
	    for (Token t : listTokens) {
	        if (t.getValue().equals(token)) {
	        	logger.info("Token verificado");
	            verify = true;
	            break;
	        }
	    }
	    
	    if(verify == true) {
	    	return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	    }
	    else {
	    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
	    }
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteToken(@Context HttpServletRequest request) {
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
	        }
	    }
	    String response;
	    if(idU != -1) {
	    	tokenDAO.delete(idU);
	    	logger.info("Tokens del usuario: " + idU +  "eliminados");
	    	return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	    }
	    else {
	    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
	    }
	}

    private String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private java.sql.Date calculateExpiryDate() {
        long currentTime = System.currentTimeMillis();
        // 1 hora en milisegundos
        long expirationTime = currentTime + (60 * 60 * 1000); 
        return new java.sql.Date(expirationTime);
    }
}