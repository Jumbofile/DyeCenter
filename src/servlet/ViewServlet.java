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
            getData(req, resp);

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
            //getData(req, resp);
            postData(resp);
            //resp.sendRedirect(req.getContextPath() + "/view");
            req.getSession().setAttribute("gid", gid);
        }
    }

    public void getData(HttpServletRequest req, HttpServletResponse resp) throws IOException{
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
        if(game.getStatus() == 1) {
            if (game.getTeam1Score() > game.getTeam2Score()) {
                req.setAttribute("teamWon", "Blue team won.");
            } else if (game.getTeam1Score() < game.getTeam2Score()) {
                req.setAttribute("teamWon", "Red team won.");
            } else {
                req.setAttribute("teamWon", "Draw.");
            }
            req.setAttribute("caption", "This game has ended, stats can not be edited.");
        }else{
            req.setAttribute("teamWon", "Game in progress.");
            req.setAttribute("caption", "This game is in progress, score will update live.");
        }
        //set hash value
        req.setAttribute("gameHash", game.getHash());

        resp.setContentType("text/plain");
        resp.getWriter().println(game.getTeam1Score()+","+game.getTeam2Score());
    }

    public void postData(HttpServletResponse resp) throws IOException{
        Account account = new Account();
        account.populateAccountData(Integer.parseInt(uid));
        Player player = new Player(account.getUsername());

        Table table = new Table(Integer.parseInt(tid));

        Game game = new Game(Integer.parseInt(gid), table.getTID());
        String data = new String();

        //team scores
        data = game.getTeam1Score() + ",";
        data = data + game.getTeam2Score() + ",";

        //player scores
        System.out.println("GREG!" + game.getPlayer1Score());
        data = data + game.getPlayer1Score() + ",";
        data = data + game.getPlayer2Score() + ",";
        data = data + game.getPlayer3Score() + ",";
        data = data + game.getPlayer4Score() + ",";

        //player plunks
        data = data + game.getPlayer1Plunks() + ",";
        data = data + game.getPlayer2Plunks() + ",";
        data = data + game.getPlayer3Plunks() + ",";
        data = data + game.getPlayer4Plunks() + ",";

        //player win loss
        data = data + game.getPlayer1().getWinLossRatio() + ",";
        data = data + game.getPlayer2().getWinLossRatio() + ",";
        data = data + game.getPlayer3().getWinLossRatio() + ",";
        data = data + game.getPlayer4().getWinLossRatio() + ",";

        //header text
        if(game.getStatus() == 1) {
            if (game.getTeam1Score() > game.getTeam2Score()) {
                data = data + "Blue team won." + ",";

            } else if (game.getTeam1Score() < game.getTeam2Score()) {
                data = data + "Red team won." + ",";
            } else {
                data = data + "Draw." + ",";
            }
            data = data + "This game has ended, scores are final.";
        }else{
            data = data + "Game in progress." + ",";
            data = data + "This game is in progress, score will update live.";
        }
        resp.setContentType("text/plain");
        resp.getWriter().println(data);
    }
}
