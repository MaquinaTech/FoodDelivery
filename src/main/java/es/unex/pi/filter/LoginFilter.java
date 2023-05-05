package es.unex.pi.filter;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import es.unex.pi.model.Token;


/**
 * Servlet Filter implementation class LoginFilter
 */

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }, urlPatterns = { "/rest/*" })
public class LoginFilter implements Filter {
	//private static final Logger logger = Logger.getLogger(Filter.class.getName());
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

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String tokenValue = authHeader.substring("Bearer ".length()).trim();
        //TODO Recuperamos token del usuario en la BD
        //Token token = DAOToken.getToken(tokenValue);
        
        //TODO descomentar esto
        /*if (token == null) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        if (token.getExpiryDate().before(new Date())) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired token");
            return;
        }*/

        chain.doFilter(request, response);
     }
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}
}

