package model;

import java.util.Date;

public class Account {
	private int accountId;
	private String username;
	private String password;
	private Date createdAt;
	
	public Account () {
		
	}
	
	public Account (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public boolean login (String inputPassword) {
		return password != null && password.equals(inputPassword);
	}
	
	//Getter
	public int getAccountId() {
		return accountId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	//Setter
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
