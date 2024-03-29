package es.unex.pi.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;
import es.unex.pi.model.Token;
import es.unex.pi.model.User;
import es.unex.pi.dao.JDBCTokenDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.TokenDAO;
import es.unex.pi.dao.UserDAO;
import java.util.logging.Logger;
import jakarta.servlet.GenericServlet;


/**
 * Servlet Filter implementation class LoginFilter
 */

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }, urlPatterns = { "/rest/*" })
public class LoginFilter implements Filter {
	//private static final Logger logger = Logger.getLogger(Filter.class.getName());
	//@Context
	//ServletContext sc;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
	private ServletContext servletContext;

    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    String loginPath = httpRequest.getContextPath() + "/rest/auth";
	    String verifyPath = httpRequest.getContextPath() + "/rest/auth/verify";
	    String registerPath = httpRequest.getContextPath() + "/rest/auth/register";
	    
	
	    if (httpRequest.getRequestURI().equals(loginPath) || httpRequest.getRequestURI().equals(verifyPath) || httpRequest.getRequestURI().equals(registerPath)  ) {
	        chain.doFilter(request, response);
	        return;
	    }
	
	    String authHeader = httpRequest.getHeader("Authorization");
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
	        return;
	    }
	
	    String tokenValue = authHeader.substring("Bearer ".length()).trim();
	        
	    Connection conn = (Connection) servletContext.getAttribute("dbConn");
	    JDBCTokenDAOImpl tokenDAO = new JDBCTokenDAOImpl();
	    tokenDAO.setConnection(conn);
	    List<Token> listTokens = tokenDAO.getAll();	    
	    boolean verify = false;
	    for (Token t : listTokens) {
	        if (t.getValue().equals(tokenValue)) {
	        	verify = true;
	            break;
	        }
	    }
	        
	    if (!verify) {
	        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
	        return;
	    }
	
	    chain.doFilter(request, response);
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}
}

