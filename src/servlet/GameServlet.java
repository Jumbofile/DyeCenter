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
import java.sql.SQLException;
import java.util.ArrayList;

public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private String gid = null ;
    private String tid = null ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Game DoGet");
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

            if(game.getStatus() == 1){
                resp.sendRedirect(req.getContextPath() + "/view");
            }

            String htmlForPage = "";
            tid = String.valueOf(table.getTID());

            //player names
            req.setAttribute("t1p1Name", game.getPlayer1().getName());
            req.setAttribute("t1p2Name", game.getPlayer2().getName());
            req.setAttribute("t2p1Name", game.getPlayer3().getName());
            req.setAttribute("t2p2Name", game.getPlayer4().getName());

            //player usernames
            req.setAttribute("t1p1Username", game.getPlayer1().getUsername());
            req.setAttribute("t1p2Username", game.getPlayer2().getUsername());
            req.setAttribute("t2p1Username", game.getPlayer3().getUsername());
            req.setAttribute("t2p2Username", game.getPlayer4().getUsername());

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

            //set hash value
            req.setAttribute("gameHash", game.getHash());


        }try {

            req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
            req.getSession().setAttribute("gid", gid);
            req.getSession().setAttribute("uid", uid);
        }catch (Exception e){
            //should really try to name this exception or possible remove it
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        gid = (String) req.getSession().getAttribute("gid");
        System.out.println("Game DoPost");

        if (uid == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }else {
            Table table = new Table(Integer.parseInt(tid));

            Game game = new Game(Integer.parseInt(gid), table.getTID());

            String points = (String) req.getParameter("points");
            System.out.println("Points: " + points);
            String playerFocus = (String) req.getParameter("playerFocus");
            System.out.println("Player: " + playerFocus);

            //game finish was requested
            if (points.equals("finish")) {
                //finish the game
                game.endGame();
                resp.sendRedirect(req.getContextPath() + "/view");
                req.getSession().setAttribute("gid", gid);
            } else if (playerFocus != null && (!playerFocus.equals(""))) {
                //cannot edit stats if status isnt 0
                if(game.getStatus() == 0) {
                    if(!points.equals("")) {

                        //see if the points are plunks
                        if (points.indexOf('$') != -1) {
                            int pointAmount = Integer.parseInt(points.substring(1));
                            if (pointAmount < 0) {
                                game.updatePlayerPlunk(playerFocus, -1);
                                game.updatePlayerScore(playerFocus, -1 * table.getPlunkAmount());
                            } else {
                                game.updatePlayerPlunk(playerFocus, 1);
                                game.updatePlayerScore(playerFocus, table.getPlunkAmount());
                            }
                        } else {
                            int pointAmount = Integer.parseInt(points);
                            if (pointAmount < 0) {
                                game.updatePlayerScore(playerFocus, -1);
                            } else {
                                game.updatePlayerScore(playerFocus, 1);
                            }
                        }
                        //call the db and update the game score
                        game.updateGameScore(game);
                    }
                }
            }
            postData(resp);
            req.getSession().setAttribute("gid", gid);
        }

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
        data = data + game.getPlayer1Score() + ",";
        data = data + game.getPlayer2Score() + ",";
        data = data + game.getPlayer3Score() + ",";
        data = data + game.getPlayer4Score() + ",";

        //player plunks
        data = data + game.getPlayer1Plunks() + ",";
        data = data + game.getPlayer2Plunks() + ",";
        data = data + game.getPlayer3Plunks() + ",";
        data = data + game.getPlayer4Plunks() + ",";

        resp.setContentType("text/plain");
        resp.getWriter().println(data);
    }

}
