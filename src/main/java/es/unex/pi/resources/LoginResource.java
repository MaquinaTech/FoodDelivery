package es.unex.pi.resources;

import java.util.Base64;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.logging.Logger;
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
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import es.unex.pi.model.Token;
import es.unex.pi.resources.exceptions.CustomBadRequestException;
import es.unex.pi.resources.exceptions.CustomNotFoundException;

@Path("/auth")
public class LoginResource {

    @POST
    @Path("/login")
    public Response login(User user) {
    	//TODO
        // Aquí hay que verificar usuario como en el controller
    	return Response.ok(this.generateToken(user)).build();
    	
    }

    @POST
    @Path("/token")
    public Response generateToken(User user) {
        String tokenValue = generateRandomToken();
        Date expiryDate = calculateExpiryDate();
        Token token = new Token(tokenValue, expiryDate);

        //TODO
        // Aquí se guarda el token en la base de datos (nueva tabla en bd Token -> campos:id, idUser, token, token_expiry)
        // Tambien hay que crear un modelo, un DAO y un JDBC...
        //Opcional: Se pueden añadir los campos token y expiryDate a la tabla user pero hay que cambiar la manera de tratar los tokens 
        //(actualmente es una clase Token, simplemente deberia ser un String y un Date))

        return Response.ok(token).build();
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