package service;

import dao.AccountDAO;
import model.Account;

public class AuthService {
	private final AccountDAO accountDAO = new AccountDAO();
	
	public Account login(String username, String password) {
		return accountDAO.login(username, password);
	}
	
	public boolean isAuthenticated(Account account) {
		return account != null;
	}
}
