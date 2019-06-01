package servlet;

import backend.DatabaseProvider;
import backend.DatabaseController;
import backend.IDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileServlet extends HttpServlet {
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

            System.out.println("Profile Servlet: doGet");
            DatabaseProvider.setInstance(new DatabaseController());
            IDatabase db = DatabaseProvider.getInstance();


            try {
                accountInfo = db.getCardAccountData(username);
                String pic = accountInfo.get(8);
                if(pic.equals("pinkPonies")){
                    req.setAttribute("img", "http://cnam.ca/wordpress/wp-content/uploads/2018/06/default-profile.gif");
                }else{
                    req.setAttribute("img", pic);
                }
                req.setAttribute("username", accountInfo.get(1));
                req.setAttribute("email", accountInfo.get(3));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            req.getRequestDispatcher("/_view/profile.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("Profile Servlet: doPost");

    }
}
