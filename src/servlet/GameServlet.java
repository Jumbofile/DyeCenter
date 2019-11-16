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
        gid = req.getSession().getAttribute("gid").toString() ;
        tid = req.getSession().getAttribute("tid").toString();
        System.out.println("GID: " + gid);
        if(gid == null || gid == "-1") {
            req.getRequestDispatcher("/dashboard").forward(req, resp);
        }

        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            Account account = new Account();
            account.populateAccountData(Integer.parseInt(uid));
            Player player = new Player(account.getUsername());

            //todo this is null
            Table table = new Table(Integer.parseInt(tid));

            Game game = new Game(Integer.parseInt(gid), table.getTID());

            String htmlForPage = "";
            tid = String.valueOf(table.getTID());


            req.setAttribute("t1p1Name", game.getPlayer1().getName());
            req.setAttribute("t1p2Name", game.getPlayer2().getName());
            req.setAttribute("t2p1Name", game.getPlayer3().getName());
            req.setAttribute("t2p2Name", game.getPlayer4().getName());

            req.setAttribute("t1p1Score", game.getPlayer1Score());
            req.setAttribute("t1p2Score", game.getPlayer2Score());
            req.setAttribute("t2p1Score", game.getPlayer3Score());
            req.setAttribute("t2p2Score", game.getPlayer4Score());

            req.setAttribute("t1Score", game.getTeam1Score());
            req.setAttribute("t2Score", game.getTeam2Score());

            req.setAttribute("t1p1WLR", game.getPlayer1().getWinLossRatio());
            req.setAttribute("t1p2WLR", game.getPlayer2().getWinLossRatio());
            req.setAttribute("t1p2WLR", game.getPlayer3().getWinLossRatio());
            req.setAttribute("t2p2WLR", game.getPlayer4().getWinLossRatio());


            //req.setAttribute("t1p1UID", t1p1UID);



            }
        try {
            req.getSession().setAttribute("gid", gid);
            req.getSession().setAttribute("uid", uid);
            req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        }catch (Exception e){

        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        gid = (String) req.getSession().getAttribute("gid");


        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        }else{

            Account account = new Account();
            account.populateAccountData(Integer.parseInt(uid));
            Player player = new Player(account.getUsername());

            Table table = new Table(Integer.parseInt(tid));

            Game game = new Game(Integer.parseInt(gid), table.getTID());

            String points = (String)req.getParameter("point");
            int playerBeingModified = Integer.parseInt(req.getParameter("player"));
            if(points.equals("finish")){
                //finish the game
                try {
                    ArrayList<String> gameStats = db.getGameStats(Integer.parseInt(gid));
                    int t1Score = Integer.parseInt(gameStats.get(2)) ;
                    int t2Score = Integer.parseInt(gameStats.get(3)) ;

                    System.out.println("t1: " + t1Score + " t2:" + t2Score);
                    //get players
                    String[] t1Players = gameStats.get(0).split(",") ;
                    String[] t2Players = gameStats.get(1).split(",") ;

                    // UID values for team 1
                    String t1p1UID = t1Players[0].split("~")[0];
                    String t1p2UID = t1Players[1].split("~")[0];

                    // UID values for team 2
                    String t2p1UID = t2Players[0].split("~")[0];
                    String t2p2UID = t2Players[1].split("~")[0];

                    int[] team1 = new int[]{Integer.parseInt(t1p1UID), Integer.parseInt(t1p2UID)};
                    int[] team2 = new int[]{Integer.parseInt(t2p1UID), Integer.parseInt(t2p2UID)};
                    if(t1Score > t2Score) {
                        System.out.println("team1 won");
                        db.setWinners(team1, 1, Integer.parseInt(gid));
                        db.setLosers(team2, 1, Integer.parseInt(gid));
                    }else{
                        System.out.println("team2 won");
                        db.setWinners(team2, 2, Integer.parseInt(gid));
                        db.setLosers(team1, 2, Integer.parseInt(gid));
                    }
                }catch(Exception e){

                }

            }else {
                if (playerBeingModified  != -1) {
                    System.out.println("point: " + points);
                    System.out.println("player: " + player);
                    System.out.println("Len: " + points.length());

                    try {


                        if (playerBeingModified  <= 1) {
                            if (points.charAt(points.length() - 1) == '~') {
                                int onlyPoint = Integer.parseInt(points.substring(0, points.length() - 1));
                                System.out.println("point: " + onlyPoint);
                                db.updateUserPoints(onlyPoint, players[player]);
                                db.updateGameScore(onlyPoint, 1, Integer.parseInt(gid), players[player]);
                                db.updateUserPlunks(players[player], 0);
                            } else {
                                db.updateGameScore(Integer.parseInt(points), 1, Integer.parseInt(gid), players[player]);
                                db.updateUserPoints(Integer.parseInt(points), players[player]);
                            }
                        } else {
                            if (points.charAt(points.length() - 1) == '~') {
                                int onlyPoint = Integer.parseInt(points.substring(0, points.length() - 1));
                                db.updateUserPoints(onlyPoint, players[player]);
                                db.updateGameScore(onlyPoint, 1, Integer.parseInt(gid), players[player]);
                                db.updateUserPlunks(players[player], 1);
                            } else {
                                db.updateGameScore(Integer.parseInt(points), 2, Integer.parseInt(gid), players[player]);
                                db.updateUserPoints(Integer.parseInt(points), players[player]);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                resp.sendRedirect(req.getContextPath() + "/game");
                req.getSession().setAttribute("gid", gid);
            } catch (Exception e) {

            }

        }

    }
}