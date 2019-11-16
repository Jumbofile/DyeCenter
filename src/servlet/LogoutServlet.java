package servlet;

import backend.Database.DatabaseFactory;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/index");
	}
}

