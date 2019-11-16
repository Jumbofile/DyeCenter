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

            String points = (String)req.getParameter("card-data-1");
            System.out.println(points);
            //int playerBeingModified = Integer.parseInt(req.getParameter("player"));
            String[] data1 = req.getParameter("card-data-1").toString().split(",");
            String[] data2 = req.getParameter("card-data-2").toString().split(",");
            String[] data3 = req.getParameter("card-data-3").toString().split(",");
            String[] data4 = req.getParameter("card-data-4").toString().split(",");


            //update player 1
            game.setPlayer1Score(Integer.parseInt(data1[2]));
            game.setPlayer1Plunks(Integer.parseInt(data1[3]));

            //update player 2
            game.setPlayer2Score(Integer.parseInt(data2[2]));
            game.setPlayer2Plunks(Integer.parseInt(data2[3]));

            //update player 3
            game.setPlayer3Score(Integer.parseInt(data3[2]));
            game.setPlayer3Plunks(Integer.parseInt(data3[3]));

            //update player 4
            game.setPlayer4Score(Integer.parseInt(data4[2]));
            game.setPlayer4Plunks(Integer.parseInt(data4[3]));

            //team 1 score
            game.setTeam1Score(Integer.parseInt(data1[2]) + Integer.parseInt(data2[2]));

            //team 2 score
            game.setTeam2Score(Integer.parseInt(data3[2]) + Integer.parseInt(data4[2]));

            game.updateGameScore(game);
            }
            try {
                resp.sendRedirect(req.getContextPath() + "/game");
                req.getSession().setAttribute("gid", gid);
            } catch (Exception e) {

            }

        }

    }
