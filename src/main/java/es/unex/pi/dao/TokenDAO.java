package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.Date;
import es.unex.pi.model.Token;

public interface TokenDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets a category from the DB using id.
	 * 
	 * @param value
	 * Token Identifier.
	 * @param expiryDate
	 * Token expiry date.
	 * 
	 * @return Token object with that value.
	 */
	public Token get(String value);
	
	/**
	 * Add token to DB.
	 * 
	 * @param token
	 * Token.
	 * 
	 * @return If Token add correctly.
	 */
	public boolean add(Token token);
	
	/**
	 * Add token to DB.
	 * 
	 * @param token
	 * Token.
	 * 
	 * @return If Token add correctly.
	 */
	public boolean update(Token token);
	
	/**
	 * Verify token in DB.
	 * 
	 * @param value
	 * Token value.
	 * 
	 * @return If Token is active.
	 */
	public boolean verify(String value);
	
}