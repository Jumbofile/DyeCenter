package servlet;

import backend.DatabaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private DatabaseController db = new DatabaseController();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Game DoGet");
        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            String tableID = (String)req.getSession().getAttribute("tableID");
            System.out.println("get: " + tableID);
			//req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            try{
                //get the tables from your username and display them if the exist
                //pass in GId based on TID
                String htmlForPage = "";
                ArrayList<Integer> gamesOnTable = db.getGames(Integer.parseInt(tableID));
                for(int i = 0; i < gamesOnTable.size(); i++){
                    //System.out.println("Game :" + i);
                    int forPrinting = i+1;
                    htmlForPage = htmlForPage +
                    "<br>"+
                    "<button type=\"submit\" name = \"gamePressed\" value = \"" + gamesOnTable.get(i) + "\">Game " + forPrinting + "</button>";

                }
                //System.out.println(htmlForPage);
                req.setAttribute("gameButtons", htmlForPage);
            }catch(Exception e){

            }
            req.setAttribute("tableID", tableID);
            req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        uid = (String) req.getSession().getAttribute("uid"); //session stuff

        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        }else {
            //int tableID = (int)req.getAttribute("tableId");
            String gameID = (String)req.getSession().getAttribute("GID");
            System.out.println(gameID);
            try{

                }catch(Exception e){

            }
            String loadGame = req.getParameter("gamePressed");
            //System.out.println("YEET" + req.getParameter("t1"));
            if(req.getParameter("t1").equals("") || req.getParameter("t2").equals("") && !(loadGame.equals(""))){
                //Game pressed below

                System.out.println(loadGame);
                try {
                    ArrayList<String> gameInfo = db.getGameStats(Integer.parseInt(loadGame));
                    for (String st: gameInfo
                         ) {
                        System.out.println(st);
                    }
                }
                catch(Exception e){

                }
            }else {
                //Create game funtions below
                String[] t1 = req.getParameter("t1").split(",");
                String[] t2 = req.getParameter("t2").split(",");

                //add players to game
                ArrayList<String> team1 = new ArrayList<String>();
                team1.add(t1[0]);
                team1.add(t1[1]);
                ArrayList<String> team2 = new ArrayList<String>();
                team2.add(t2[0]);
                team2.add(t2[1]);
                //create game
                try {
                    db.createGame(Integer.parseInt(gameID), team1, team2);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Game create fail.");
                }
            }
           // req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            req.setAttribute("GID", gameID);
            req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        }

    }
}