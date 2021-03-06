package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.Entities.Account;
import org.mindrot.jbcrypt.BCrypt;

import backend.Database.DatabaseProvider;
import backend.Database.IDatabase;
//import backend.hashSHA256;
//import fakeDB.FakeUserDB;
import backend.Database.DatabaseFactory;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Register Servlet: doGet");
		
		req.getRequestDispatcher("/_view/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Register Servlet: doPost");
		
		//checks if account it a real account
		//fake db stuff//FakeUserDB db = new FakeUserDB();
		DatabaseProvider.setInstance(new DatabaseFactory()); // some of this code taken from lab 06 and library example ---- CITING
		IDatabase db = DatabaseProvider.getInstance();

		// gets username and password
		String user = (req.getParameter("u")).toLowerCase();
		String password = req.getParameter("p");
		String password2 = req.getParameter("p2");
		String email = req.getParameter("e");
		String name = req.getParameter("n");
		
		//checks if account exist
		boolean validAccount = false;
	
		//Checks if 2 passes are the same
		if(password.equals(password2)){
			password = BCrypt.hashpw(password, BCrypt.gensalt());
			Account account = new Account();
			validAccount = account.registerAccount(user, password, email, name);
		}else{
			validAccount = false;
		}
		
		//If account is valid, continue, if it isnt, spit out error
		if(validAccount == true){
			// Forward to view to render the result HTML document
			/*
			 * make this automatically login for you
			 */
			//backend.Email.email();
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			System.out.println("Register Servlet: Register Successful");
		}else{
			req.setAttribute("response", "<div id='error'>Email already in use or passwords don't match!</div>");
			System.out.println("Register Servlet: Register Failed");
			req.getRequestDispatcher("/_view/register.jsp").forward(req, resp);
		}
		//System.out.println(first + second);
		
	}

}
