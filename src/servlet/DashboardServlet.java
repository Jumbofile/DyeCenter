package servlet;

import backend.DatabaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
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
        System.out.println(sessionuid);
        if (sessionuid == null) {
            req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
        } else {

            setAttr(req, resp);

            req.getRequestDispatcher("/_view/dashboard.jsp").forward(req, resp);
        }
    }

    // select  UID, (wins+loss) as Games from userstats where uid = 1
    // select points from userstats where UID = 1
    // select TID from dyetable where uid = 1

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //System.out.println(sessionuid);
        sessionuid = (String) req.getSession().getAttribute("uid"); //session stuff


        System.out.println("Tableid: " + req.getParameter("tid"));
        String tableID = req.getParameter("tid");
        if(tableID != null){
            resp.sendRedirect(req.getContextPath() + "/table");
            req.getSession().setAttribute("tableID", tableID);
        }else{
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
    }

    private void setAttr(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException  {

        if(this.sessionuid != null) {
            Integer UID = Integer.parseInt(sessionuid) ;

            try{
                // Stat Attributes
                ArrayList<Integer> userStats = db.getUserStats(UID);
                    req.setAttribute("played", userStats.get(2) + userStats.get(3));
                    req.setAttribute("points",userStats.get(0));
                    req.setAttribute("plunks",userStats.get(1));
                    req.setAttribute("wins",userStats.get(2));
                    req.setAttribute("loss",userStats.get(3));

                // Display Name
                String displayName = db.getAccountName(UID) ;
                    req.setAttribute("name",displayName);

                // Table Attributes
                ArrayList<Integer> tables = db.getTables(UID) ;
                ArrayList<String> tblNames = new ArrayList<>() ;
                ArrayList<Integer> uniqueEntries = new ArrayList<Integer>();
                for(Integer IDs : tables){
                    if(uniqueEntries.contains(IDs)){
                        //do nothing
                    }else{
                        uniqueEntries.add(IDs);
                    }
                }
                for(Integer TID : uniqueEntries) {
                    System.out.println("TID: " + TID);
                    tblNames.add( db.getTableNameBasedOnID(TID) + "^" + TID ) ;
                }
                String tblcsv = String.join(",", tblNames);


                //Set Attributes

                    //UserStat
                    req.setAttribute("played", userStats.get(2) + userStats.get(3));
                    req.setAttribute("points",userStats.get(0));
                    req.setAttribute("plunks",userStats.get(1));
                    req.setAttribute("wins",userStats.get(2));
                    req.setAttribute("loss",userStats.get(3));

                    //Table
                    req.setAttribute("tableNames", tblcsv);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
