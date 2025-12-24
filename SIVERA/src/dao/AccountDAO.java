package dao;

import config.DatabaseConfig;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
	private static final String LOGIN_QUERY = "SELECT * FROM accounts WHERE username = ? AND password = ?";
	
	public Account login(String username, String password) {
		Account account = null;
		
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(LOGIN_QUERY)){
			
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setUsername(rs.getString("username"));
				account.setPassword(rs.getString("password"));
				account.setCreatedAt(rs.getTimestamp("created_at"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}
}
