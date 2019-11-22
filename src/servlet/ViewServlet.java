package servlet;

import backend.Entities.Account;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private String gid = null ;
    private String tid = null ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("View DoGet");
        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        try {
            gid = req.getSession().getAttribute("gid").toString();
            tid = req.getSession().getAttribute("tid").toString();
        }catch(Exception e){
            req.getRequestDispatcher("/dashboard").forward(req, resp);
        }

        if (uid == null) {
            try {
                resp.sendRedirect(req.getContextPath() + "/login");
            }catch( IllegalStateException e){}

        } else {
            Account account = new Account();
            account.populateAccountData(Integer.parseInt(uid));
            Player player = new Player(account.getUsername());

            Table table = new Table(Integer.parseInt(tid));

            Game game = new Game(Integer.parseInt(gid), table.getTID());

            String htmlForPage = "";
            tid = String.valueOf(table.getTID());
            //todo - cant put 2 of the same usernames in the same game
            //player names
            req.setAttribute("t1p1Name", game.getPlayer1().getName());
            req.setAttribute("t1p2Name", game.getPlayer2().getName());
            req.setAttribute("t2p1Name", game.getPlayer3().getName());
            req.setAttribute("t2p2Name", game.getPlayer4().getName());

            //player scores
            req.setAttribute("t1p1Score", game.getPlayer1Score());
            req.setAttribute("t1p2Score", game.getPlayer2Score());
            req.setAttribute("t2p1Score", game.getPlayer3Score());
            req.setAttribute("t2p2Score", game.getPlayer4Score());

            //player scores
            req.setAttribute("t1p1Plunks", game.getPlayer1Plunks());
            req.setAttribute("t1p2Plunks", game.getPlayer2Plunks());
            req.setAttribute("t2p1Plunks", game.getPlayer3Plunks());
            req.setAttribute("t2p2Plunks", game.getPlayer4Plunks());

            //team scores
            req.setAttribute("t1Score", game.getTeam1Score());
            req.setAttribute("t2Score", game.getTeam2Score());

            //player win loss ratios
            req.setAttribute("t1p1WLR", game.getPlayer1().getWinLossRatio());
            req.setAttribute("t1p2WLR", game.getPlayer2().getWinLossRatio());
            req.setAttribute("t2p1WLR", game.getPlayer3().getWinLossRatio());
            req.setAttribute("t2p2WLR", game.getPlayer4().getWinLossRatio());

            //plunk value for the table
            req.setAttribute("plunkValue", table.getPlunkAmount());

            //set winner
            if(game.getTeam1Score() > game.getTeam2Score()){
                req.setAttribute("teamWon", "Blue team won.");
            }else if(game.getTeam1Score() < game.getTeam2Score()){
                req.setAttribute("teamWon", "Red team won.");
            }else{
                req.setAttribute("teamWon", "Draw.");
            }
            //set hash value
            req.setAttribute("gameHash", game.getHash());

        }try {
            req.getSession().setAttribute("gid", gid);
            req.getSession().setAttribute("uid", uid);
            req.getRequestDispatcher("/_view/view.jsp").forward(req, resp);
        }catch (Exception e){
            //should really try to name this exception or possible remove it
            e.printStackTrace();
        }
    }

    //todo - dont allow score change when game status = 1
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        gid = (String) req.getSession().getAttribute("gid");
        System.out.println("View DoPost");

        if (uid == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }else {

            resp.sendRedirect(req.getContextPath() + "/view");
            req.getSession().setAttribute("gid", gid);
        }
    }
}