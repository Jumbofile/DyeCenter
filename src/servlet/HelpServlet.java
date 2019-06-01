package servlet;

import backend.DatabaseProvider;
import backend.DatabaseController;
import backend.IDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class HelpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String username = null;
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        username = (String) req.getSession().getAttribute("username"); //session stuff

        if (username == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {

            System.out.println("Help Servlet: doGet");
            DatabaseProvider.setInstance(new DatabaseController());
            IDatabase db = DatabaseProvider.getInstance();

            req.getRequestDispatcher("/_view/help.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        System.out.println("Profile Servlet: doPost");
        DatabaseProvider.setInstance(new DatabaseController());
        IDatabase db = DatabaseProvider.getInstance();

        backend.HelpEmail.helpEmail();

        req.getRequestDispatcher("/_view/help.jsp").forward(req, resp);

        System.out.println("Help Servlet: doPost");


    }
}



