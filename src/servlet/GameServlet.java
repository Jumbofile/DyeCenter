package servlet;

import backend.Database.DatabaseFactory;

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
    private int players[] = new int[4] ;
    private DatabaseFactory db = new DatabaseFactory();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Game DoGet");
        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        gid = (String) req.getSession().getAttribute("gid") ;
        System.out.println("GID: " + gid);
        if(gid == null || gid == "-1") {
            req.getRequestDispatcher("/dashboard").forward(req, resp);
        }

        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            try {

                String htmlForPage = "";
                ArrayList<String> gameStats = db.getGameStats(Integer.parseInt(gid));
                tid = gameStats.get(6) ;

                //get plunk value
                int plunkValue = db.getPlunk(Integer.parseInt(tid));
                req.setAttribute("plunkValue", plunkValue);

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

                ArrayList<Integer> t1p1Stats = db.getUserStats(Integer.parseInt(t1p1UID)) ;
                ArrayList<Integer> t1p2Stats = db.getUserStats(Integer.parseInt(t1p2UID)) ;
                ArrayList<Integer> t2p1Stats = db.getUserStats(Integer.parseInt(t2p1UID)) ;
                ArrayList<Integer> t2p2Stats = db.getUserStats(Integer.parseInt(t2p2UID)) ;

                float t1p1WLR = 0 ;
                float t1p2WLR = 0 ;
                float t2p1WLR = 0 ;
                float t2p2WLR = 0 ;

                t1p1WLR = (float)t1p1Stats.get(2) / (float)(t1p1Stats.get(3) + t1p1Stats.get(2)) ;
                t1p1WLR = Math.round(t1p1WLR * 100.0);

                players[0] = Integer.parseInt(t1p1UID);
                players[1] = Integer.parseInt(t1p2UID);
                players[2] = Integer.parseInt(t2p1UID);
                players[3] = Integer.parseInt(t2p2UID);

                req.setAttribute("t1p1Name", db.getAccountName(Integer.parseInt(t1p1UID)));
                req.setAttribute("t1p2Name", db.getAccountName(Integer.parseInt(t1p2UID)));
                req.setAttribute("t2p1Name", db.getAccountName(Integer.parseInt(t2p1UID)));
                req.setAttribute("t2p2Name", db.getAccountName(Integer.parseInt(t2p2UID)));

                req.setAttribute("t1p1Score", t1p1Score);
                req.setAttribute("t1p2Score", t1p2Score);
                req.setAttribute("t2p1Score", t2p1Score);
                req.setAttribute("t2p2Score", t2p2Score);

                req.setAttribute("t1Score", t1Score);
                req.setAttribute("t2Score", t2Score);

                req.setAttribute("t1p1WLR", t1p1WLR);
//                req.setAttribute("t1p2WLR", t1p2WLR);
//                req.setAttribute("t1p2WLR", t1p2WLR);
//                req.setAttribute("t2p2WLR", t2p2WLR);


            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                req.getSession().setAttribute("gid", gid);
                req.getSession().setAttribute("uid", uid);
                req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
            }catch (Exception e){

            }
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
            String points = (String)req.getParameter("point");
            int player = Integer.parseInt(req.getParameter("player"));
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
                if (player != -1) {
                    System.out.println("point: " + points);
                    System.out.println("player: " + player);
                    System.out.println("Len: " + points.length());

                    try {


                        if (player <= 1) {
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