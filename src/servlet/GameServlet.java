package servlet;

import backend.DatabaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private String gid = null ;
    private String tid = null ;
    private DatabaseController db = new DatabaseController();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Game DoGet");
        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        gid = (String) req.getSession().getAttribute("gid") ;
        if(gid == null || gid == "-1") {
            req.getRequestDispatcher("/dashboard").forward(req, resp);
        }

        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            try {

                String htmlForPage = "";
                ArrayList<String> gameStats = db.getGameStats(Integer.parseInt(gid));
                tid = gameStats.get(5) ;
                String[] t1Players = gameStats.get(0).split(",") ;
                String[] t2Players = gameStats.get(1).split(",") ;

                String t1Score = gameStats.get(2) ;
                String t2Score = gameStats.get(3) ;

                String status = gameStats.get(4) ;

                // Score values for team 1 players
                String t1p1Score = t1Players[0].split("~")[1];
                String t1p2Score = t1Players[1].split("~")[1];

                // UID values for team 1
                String t1p1UID = t1Players[0].split("~")[0];
                String t1p2UID = t1Players[1].split("~")[0];

                // Score values for team 2 player
                String t2p1Score = t2Players[0].split("~")[1];
                String t2p2Score = t2Players[1].split("~")[1];

                // UID values for team 2
                String t2p1UID = t2Players[0].split("~")[0];
                String t2p2UID = t2Players[1].split("~")[0];

                req.setAttribute("t1p1Name", db.getAccountName(Integer.parseInt(t1p1UID)));
                req.setAttribute("t1p2Name", db.getAccountName(Integer.parseInt(t1p2UID)));
                req.setAttribute("t2p1Name", db.getAccountName(Integer.parseInt(t2p1UID)));
                req.setAttribute("t2p2Name", db.getAccountName(Integer.parseInt(t2p2UID)));

                req.setAttribute("t1p1Score", t1p1Score);
                req.setAttribute("t1p2Score", t1p2Score);
                req.setAttribute("t2p1Score", t2p1Score);
                req.setAttribute("t2p2Score", t2p2Score);

                //System.out.println("Game :" + i);
//                htmlForPage =  "<div class='container'>";
//                htmlForPage += "    <div class=row>" ;
//                htmlForPage += "        <div class='col-md'>";
//                htmlForPage += "            <button id='t1p1' class='player btn btn-primary'>Player "+ t1p1UID +" has "+ t1p1Score +" Points</button>";
//                htmlForPage += "        </div>" ;
//                htmlForPage += "     </div>" ;
//                htmlForPage += "        "
//                htmlForPage += "        <div class='col-md'>";
//                htmlForPage += "    <span id=team1tag class='teamTag'>Team 1</span>" ;
//                htmlForPage += "    <span id='t1p2' class='player btn btn-primary'>Player "+ t1p2UID +" has "+ t1p2Score +" Points</span>";
//                htmlForPage += "    <span id='t2p1' class='player btn btn-primary'>Player "+ t2p1UID +" has "+ t2p1Score +" Points</span>";
//                htmlForPage += "    <span id=team2tag class='teamTag'>Team 2</span>" ;
//                htmlForPage += "    <span id='t2p2' class='player btn btn-primary'>Player "+ t2p2UID +" has "+ t2p2Score +" Points</span>";
//                htmlForPage =  "</div >";
//
////                            "<br>";
////                           "<button type=\"submit\" name = \"gamePressed\" value = \"" + gamesOnTable.get(i) + "\">Game " + forPrinting + "</button>";
//
//                req.setAttribute("gameStats", htmlForPage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            req.setAttribute("gid", gid);
            req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        uid = (String) req.getSession().getAttribute("uid"); //session stuff

        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        }

    }
}