package servlet;

import backend.Database.DatabaseFactory;
import backend.Entities.Account;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String sessionuid;
    private DatabaseFactory db = new DatabaseFactory();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //sets the session id
        sessionuid = (String) req.getSession().getAttribute("uid"); //session stuff
        //System.out.println(sessionuid);
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


        //System.out.println("Tableid: " + req.getParameter("tid"));
        String tableID = req.getParameter("tid");
        if(tableID != null){
            resp.sendRedirect(req.getContextPath() + "/table");
            req.getSession().setAttribute("tableID", tableID);
        }else{
            //username = "foobar";
            if (sessionuid == null) {
                req.getRequestDispatcher("/login").forward(req, resp);
            } else {
                String hash = new String();
                hash = req.getParameter("loadGame");
                if(!(hash.equals("")) && hash != null){
                    Game game = new Game();
                    int gid = game.getGIDFromHash(hash);
                    int tid = game.getTIDFromGID(gid);
                    System.out.println("DB GID: " + gid);
                    System.out.println("DB TID: " + tid);
                    if(gid != -1){
                        resp.sendRedirect(req.getContextPath() + "/view");
                        req.getSession().setAttribute("tid", tid);
                        req.getSession().setAttribute("gid", gid);
                    }
                }
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
            Account account = new Account();
            account.populateAccountData(UID);
            Player player = account.getPlayerFromAccount();

            try{
                req.setAttribute("name", player.getName());
                req.setAttribute("username", player.getUsername());
                req.setAttribute("played", player.getTotalGames());
                req.setAttribute("points",player.getPoints());
                req.setAttribute("plunks",player.getPlunks());
                req.setAttribute("wins",player.getWins());
                req.setAttribute("loss",player.getLoss());

                // Display Name
                String displayName = account.getName();
                    req.setAttribute("name",displayName);

                // Table Attributes
                ArrayList<String> tblNames = new ArrayList<>() ;
                System.out.println(account.getTableIds());
                for(Integer TID : account.getTableIds()) {
                    //System.out.println("TID: " + TID);
                    Table table = new Table(TID);
                    tblNames.add(table.getName() + "^" + table.getTID()) ;
                }


                String tblcsv = String.join(",", tblNames);
                //Table
                req.setAttribute("tableNames", tblcsv);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
