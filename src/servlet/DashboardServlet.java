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
    private DatabaseController db = new DatabaseController();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //sets the session id
        sessionuid = (String) req.getSession().getAttribute("uid"); //session stuff

        if (sessionuid == null) {
            req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
        } else {

            setAttr(req, resp);


//            try{
//                System.out.println("SUID: " + sessionuid);
//                ArrayList<Integer> stats = db.getUserStats(Integer.parseInt(sessionuid));
//
//                req.setAttribute("points",stats.get(0));
//                req.setAttribute("plunks",stats.get(1));
//                req.setAttribute("wins",stats.get(2));
//                req.setAttribute("loss",stats.get(3));
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }

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

            setAttr(req, resp);
           // req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            req.getRequestDispatcher("/_view/dashboard.jsp").forward(req, resp);
        }

    }

    private void setAttr(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException  {

        if(this.sessionuid != null) {
            try{
                ArrayList<Integer> stats = db.getUserStats(Integer.parseInt(sessionuid));

                req.setAttribute("played", stats.get(2) + stats.get(3));
                req.setAttribute("points",stats.get(0));
                req.setAttribute("plunks",stats.get(1));
                req.setAttribute("wins",stats.get(2));
                req.setAttribute("loss",stats.get(3));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
