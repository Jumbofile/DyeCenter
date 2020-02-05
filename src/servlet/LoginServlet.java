package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import fakeDB.FakeUserDB;
import backend.Database.DatabaseProvider;
import backend.Database.DatabaseFactory;
import backend.Database.Logger;
import backend.Database.IDatabase;
import backend.Entities.Account;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Login Servlet: doGet");

		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Logger logger = new Logger();
		System.out.println("Login Servlet: doPost");

		Account account = new Account();

		DatabaseProvider.setInstance(new DatabaseFactory()); // some of this code taken from lab 06 and library example-- CITING
		IDatabase db = DatabaseProvider.getInstance();

		
		// gets username and password
		String email = (req.getParameter("e")).toLowerCase();
		String password = req.getParameter("p");
		int uid = -1;
		
		//checks if the account is valid
		boolean validAccount = account.accountExists(email, password);

		//If account is valid, continue, if it isnt, spit out error
		if(validAccount == true){
			// Forward to view to render the result HTML document

			resp.sendRedirect(req.getContextPath() + "/dashboard");
			System.out.println("Login Servlet: Login Successful");
			try{
				uid = account.getAccountID(email);
				account.populateAccountData(uid);
				logger.addLog(account.getUsername(), uid, "LoginServlet", "POST", "DB_ACCESS", "ACCOUNT", uid, "LOGIN", account.getUsername() + " logged in.");
			}catch(Exception e){
				e.printStackTrace();
			}
			if(uid != -1) {
				req.getSession().setAttribute("uid", Integer.toString(uid)); // adds username to session
			}else{
				req.setAttribute("response", "<div id='error'>Server Error</div>");
				System.out.println("Login Servlet: Login Failed");
				req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			}
		}else{
			req.setAttribute("response", "<div id='error'>Email or password is incorrect!</div>");
			System.out.println("Login Servlet: Login Failed");
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
		//System.out.println(first + second);
	}

}