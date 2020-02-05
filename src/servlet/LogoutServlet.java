package servlet;

import backend.Database.DatabaseFactory;
import backend.Database.Logger;
import backend.Entities.Account;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String uid = null;
	private DatabaseFactory db = new DatabaseFactory();
	ArrayList<String> accountInfo = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Table DoGet");
		Account account = new Account();
		account.populateAccountData(Integer.parseInt((String)req.getSession().getAttribute("uid"))); //session stuff);
		Logger logger = new Logger();
		try {
			logger.addLog(account.getUsername(), account.getUID(account.getUsername()), "LogoutServlet", "GET", "DB_NONE", "ACCOUNT", account.getUID(account.getUsername()), "LOGOUT", account.getUsername() + " logged out, session cleared.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/index");
	}
}

