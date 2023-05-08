package es.unex.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import es.unex.pi.util.CompararValoracion;
import es.unex.pi.dao.ReviewsDAO;
import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCReviewsDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Review;
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

@Path("/reviews")
public class ReviewResources {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("/{idR}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getReviews(@PathParam("idR") long idR, @Context HttpServletRequest request) {
		ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
		Connection conn = (Connection) sc.getAttribute("dbConn");
		reviewsDAO.setConnection(conn);
		HttpSession sesion = request.getSession();
		User user = (User) sesion.getAttribute("usuario");
		List<Review> listaReviews = reviewsDAO.getAllByRestaurant(idR);
		List<Review> listaReviewsFinal = new ArrayList<>();
		for (Review r : listaReviews) {
			if (r.getIdu() != user.getId()) {
				listaReviewsFinal.add(r);
			}
		}
		return listaReviewsFinal;
	}
	
	@GET
	@Path("/FiltroValoracion/{valoracion}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> FiltroValoracion(@PathParam("valoracion") String valoracion, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
	    RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
	    restaurantDAO.setConnection(conn);
		List<Restaurant> restaurantsAll = restaurantDAO.getAll();
		if(valoracion.equals("menor")){
			Collections.sort(restaurantsAll, new CompararValoracion());
		}
		else {
			Collections.sort(restaurantsAll, Collections.reverseOrder(new CompararValoracion()));
		}
		return restaurantsAll;
	}
	
	@POST
	@Path("/CrearReview/{idR}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response CrearReview(@PathParam("idR") long idR, Review review,
			@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		User usuario = (User) sesion.getAttribute("user");
	    if(usuario.getId() == idR) {
	    	throw new WebApplicationException(Response.Status.BAD_REQUEST);
	    }
	    else { 
		    ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
		    reviewsDAO.setConnection(conn);
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
	@Path("/eliminarReview/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarReview(@PathParam("id") long id, @Context HttpServletRequest request) {
		ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HttpSession sesion = request.getSession();
		User usuario = (User) sesion.getAttribute("user"); 
		reviewsDAO.setConnection(conn);
		reviewsDAO.delete(id, usuario.getId());
		return Response.accepted(uriInfo.getAbsolutePathBuilder().build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();

	}
}
