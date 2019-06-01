package servlet;

import backend.DatabaseProvider;
import backend.DatabaseController;
import backend.IDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import backend.hashSHA256;
//import fakeDB.FakeUserDB;

public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String username;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("create Servlet: doGet");
		username = (String) req.getSession().getAttribute("username"); //session stuff

        if(username == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        }else{
            req.getRequestDispatcher("/_view/create.jsp").forward(req, resp);
        }
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("create Servlet: doPost");
		
		//checks if account it a real account
		//fake db stuff//FakeUserDB db = new FakeUserDB();
		DatabaseProvider.setInstance(new DatabaseController()); // some of this code taken from lab 06 and library example ---- CITING
		IDatabase db = DatabaseProvider.getInstance();

		// gets username and password
		String name = (req.getParameter("n"));
		String type = req.getParameter("t");
		String descs = req.getParameter("s");
		//String descl = req.getParameter("l");
		String descl = "not implementeed";
		String image = req.getParameter("i");
		String slack = req.getParameter("sk");

		System.out.println("Name:" + name + " \ntype:" + type + " \ndesc:" + descs + " \ndescl:" + descl + " \nimage:" + image +" \nslack:" + slack + " \nuser:" + username);
		//username = (String) req.getSession().getAttribute("username"); //session stuff
		
		//checks if account exist
		boolean validAccount = false;
	
		//try to create a idea

		db.insertCardData(name, descs, descl, username, image, slack, type);
			/*
			 * make this automatically login for you
			 */



        System.out.println("Create Servlet: Creation success");
        req.getRequestDispatcher("/_view/re.jsp").forward(req, resp);
		//System.out.println(first + second);
		
	}

}
