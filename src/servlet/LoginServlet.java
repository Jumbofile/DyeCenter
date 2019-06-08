package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import fakeDB.FakeUserDB;
import backend.DatabaseProvider;
import backend.DatabaseController;
import backend.IDatabase;


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
		
		System.out.println("Login Servlet: doPost");
		
		//checks if account it a real account
		//FakeUserDB db = new FakeUserDB(); fake database
		DatabaseProvider.setInstance(new DatabaseController()); // some of this code taken from lab 06 and library example-- CITING
		IDatabase db = DatabaseProvider.getInstance();

		
		// gets username and password
		String email = (req.getParameter("e")).toLowerCase();
		String password = req.getParameter("p");
		
		//checks if the account is valid
		boolean validAccount = db.accountExist(email, password);

		//If account is valid, continue, if it isnt, spit out error
		if(validAccount == true){
			// Forward to view to render the result HTML document
			resp.sendRedirect(req.getContextPath() + "/index");
			System.out.println("Login Servlet: Login Successful");
			String username = null;
			try{
			username = db.getAccountName(email);
			}catch(Exception e){
				e.printStackTrace();
			}

			req.getSession().setAttribute("username", username); // adds username to session
		}else{
			req.setAttribute("response", "<div id='error'>Email or password is incorrect!</div>");
			System.out.println("Login Servlet: Login Failed");
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
		//System.out.println(first + second);
		
		
	}

}