package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.util.CompararValoracion;
import es.unex.pi.dao.ReviewsDAO;
import es.unex.pi.dao.TokenDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.JDBCTokenDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Review;
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

@Path("/reviews")
public class ReviewResources {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviews(@FormParam("idR") Integer idR, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
		reviewsDAO.setConnection(conn);
		List<Review> listaReviews = reviewsDAO.getAllByRestaurant(idR);
		if(listaReviews.size() > 0) {
			return Response.ok(listaReviews).build();
		}
		else {
			 String emptyListJson = "{\"empty\": true, \"message\": \"No hay datos disponibles.\"}";
		        return Response.ok(emptyListJson).build();
		}
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response CrearReview(@FormParam("idR") Integer idR
			,@FormParam("comment") String comment
			,@FormParam("stars") Integer stars
			,@Context HttpServletRequest request) {
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
	    if(user == null) {
	    	throw new WebApplicationException(Response.Status.BAD_REQUEST);	
	    }
	    else { 
		    ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
		    reviewsDAO.setConnection(conn);
		    Review review = new Review();
		    if(reviewsDAO.get(idR, idU) != null) {
		    	throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
		    }
		    review.setGrade(stars);
		    review.setIdr(idR);
		    review.setIdu(idU);
		    review.setReview(comment);
		    reviewsDAO.add(review);
		    List<Review> reviewsRest = reviewsDAO.getAllByRestaurant(idR);
		    if(reviewsRest.size() != 0) {
		    	int contador = 0;
		    	for(Review r: reviewsRest) {
		    		contador+= r.getGrade();
		    	}
		    	if(contador != 0) {
		    		contador/= reviewsRest.size();
		    		contador = Math.round(contador);
		    		RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
		    	    restaurantDAO.setConnection(conn);
		    	    Restaurant restaurant = restaurantDAO.get(idR);
		    	    restaurant.setGradesAverage(contador);
		    	    restaurantDAO.update(restaurant);
		    	}
		    }
		    return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
	    }	    
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarReview(@FormParam("idR") Integer idR, @Context HttpServletRequest request) {
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
	    ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
		reviewsDAO.setConnection(conn);
		reviewsDAO.delete(idR, idU);
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();

	}
}
