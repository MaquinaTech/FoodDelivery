package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
	 * Gets a token from the DB using value.
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
	 * Gets all tokens
	 * 
	 * 
	 * @return Token object with that value.
	 */
	public List<Token> getAll();
	
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
	 * @return If Token update correctly.
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
	
	/**
	 * Delete all tokens of user in DB.
	 * 
	 * @param idU
	 * User id.
	 * 
	 * @return If Token is delete.
	 */
	public boolean delete(long idU);
	
}