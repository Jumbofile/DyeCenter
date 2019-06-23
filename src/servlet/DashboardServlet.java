package servlet;

import backend.DatabaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String sessionuid;
    private int userID = -1;
    private DatabaseController db = new DatabaseController();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //sets the session id
        sessionuid = (String) req.getSession().getAttribute("uid"); //session stuff

        //gets the int version of the UID
        userID = Integer.parseInt(sessionuid);

        //username = "foobar";
        if (sessionuid == null) {
            req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
        } else {



            //String gamesPlayed = db.getGamesPlayed();
            //req.setAttribute();

            req.getRequestDispatcher("/_view/dashboard.jsp").forward(req, resp);
        }
    }

    // select  UID, (wins+loss) as Games from userstats where uid = 1
    // select points from userstats where UID = 1
    // select TID from dyetable where uid = 1

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println(sessionuid);
        sessionuid = (String) req.getSession().getAttribute("uid"); //session stuff



        //username = "foobar";
        if (sessionuid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {


           // req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            req.getRequestDispatcher("/_view/dashboard.jsp").forward(req, resp);
        }

    }
}