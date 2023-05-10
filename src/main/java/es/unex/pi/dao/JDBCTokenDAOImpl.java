package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import es.unex.pi.model.Token;

public class JDBCTokenDAOImpl implements TokenDAO {
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCTokenDAOImpl.class.getName());

	
	@Override
	public List<Token> getAll() {
	    List<Token> tokenList = new ArrayList<>();

	    if (conn == null) return tokenList;

	    try {
	        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tokens");
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Token token = new Token(rs.getString("value"),rs.getDate("expiryDate"));
	            tokenList.add(token);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return tokenList;
	}

	
	@Override
	public Token get(String value) {
	    if (conn == null) return null;

	    try {
	        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tokens WHERE value = ?");
	        stmt.setString(1, value);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Token token = new Token(rs.getString("value"), rs.getDate("expiryDate"));
	            return token;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@Override
	public boolean add(Token token) {
	    if (conn == null || token == null) return false;

	    try {
	        PreparedStatement stmt = conn.prepareStatement("INSERT INTO tokens (value, expiryDate) VALUES (?, ?)");
	        stmt.setString(1, token.getValue());
	        stmt.setDate(2, new Date(token.getExpiryDate().getTime()));
	        int rowsAffected = stmt.executeUpdate();

	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean update(Token token) {
	    if (conn == null || token == null) return false;

	    try {
	        PreparedStatement stmt = conn.prepareStatement("UPDATE tokens SET expiryDate = ? WHERE value = ?");
	        stmt.setDate(1, new java.sql.Date(token.getExpiryDate().getTime()));
	        stmt.setString(2, token.getValue());
	        int rowsAffected = stmt.executeUpdate();

	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean verify(String value) {
	    if (conn == null || value == null) return false;
	    try {
	        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tokens WHERE value = ?");
	        stmt.setString(1, value);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            Date expiryDate = rs.getDate("expiryDate");
	            if (expiryDate != null && expiryDate.before(new java.util.Date())) {
	                // El token ha caducado, eliminarlo de la base de datos
	                PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM tokens WHERE value = ?");
	                deleteStmt.setString(1, value);
	                deleteStmt.executeUpdate();
	                return false;
	            } else {
	                return true;
	            }
	        } else {
	            return false; // El token no existe en la base de datos
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	
}