package servlet;

import backend.DatabaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TablesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String username = null;
    private DatabaseController db = new DatabaseController();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //username = (String) req.getSession().getAttribute("username"); //session stuff
        username = "foobar";
        if (username == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {



			//req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            req.getRequestDispatcher("/_view/tables.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        username = (String) req.getSession().getAttribute("username"); //session stuff

        if (username == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {


           // req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            req.getRequestDispatcher("/_view/tables.jsp").forward(req, resp);
        }

    }
}