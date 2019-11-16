package servlet;

import backend.Database.DatabaseFactory;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TablesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private DatabaseFactory db = new DatabaseFactory();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Table DoGet");
        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            String tableID = (String)req.getSession().getAttribute("tableID");
            //System.out.println("get: " + tableID);

            getGameButton(req);

            req.setAttribute("tableID", tableID);
            req.getSession().setAttribute("tid", tableID);
            req.getRequestDispatcher("/_view/tables.jsp").forward(req, resp);
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
            String tableID = (String)req.getSession().getAttribute("tableID");
            //System.out.println(tableID);

            //get game buttons
            getGameButton(req);

            String loadGame = null;

            //Check if the input boxes are empty
            if(req.getParameter("t1p1").equals("") || req.getParameter("t2p1").equals("")){
                try {
                    loadGame = req.getParameter("gamePressed");
                }catch (Exception e){
                    loadGame = null;
                }
            }

            //the input boxes are empty, the user must of clicked a game button
            if(loadGame != null){

                //Game pressed below
                resp.sendRedirect(req.getContextPath() + "/game");
                req.getSession().setAttribute("gid", loadGame);


            }else{
                //The value boxes are empty, users possibly clicked the submit button
                //We dont know if the submit was pressed so we will check if they hit a game button
                //- The game button will be pressed if the "Gamepressed" para isnt null!
                Table table = new Table(Integer.parseInt(tableID));
                if(req.getParameter("gamePressed") == null) {
                    if(req.getParameter("t1p1").equals("") || req.getParameter("t2p1").equals("")){
                        //DO NOTHING!!!
                    }else {
                        //The user pressed the submit button so we can make a game

						//get usernames and assign them to a team
						Player[] t1 = new Player[2];
						Player[] t2 = new Player[2];

						//populate team 1
						t1[0] = new Player(req.getParameter("t1p1"));
						t1[1] = new Player(req.getParameter("t1p2"));

						//populate team 2
						t2[0] = new Player(req.getParameter("t2p1"));
						t2[1] = new Player(req.getParameter("t2p2"));

                        //create game
						Game game = table.createGame(t1, t2);

						if (game != null) {
							resp.sendRedirect(req.getContextPath() + "/game");
							req.getSession().setAttribute("gid", game.getGID());
							req.getSession().setAttribute("tid", tableID);
						} else {
							//Failed to create the game, keeps user on the table view
							resp.sendRedirect(req.getContextPath() + "/table");
							req.getSession().setAttribute("tid", tableID);
						}

                    }
                }else{
                    //They actually hit a game button so just go to that game
                    resp.sendRedirect(req.getContextPath() + "/game");
                    req.getSession().setAttribute("tid", tableID);
                    req.getSession().setAttribute("gid", req.getParameter("gamePressed"));

                }
            }

            //if all else fails, get /table
            try {
                resp.sendRedirect(req.getContextPath() + "/table");
            }catch (Exception e){

            }
        }

    }

    private void getGameButton(HttpServletRequest req){
        //int tableID = (int)req.getAttribute("tableId");
        String tableID = (String)req.getSession().getAttribute("tableID");
        System.out.println("TABLE ID: " + tableID);
        int tid = Integer.parseInt(tableID);
        try{
            Table table = new Table(tid);
            //get the tables from your username and display them if the exist
            //pass in GId based on TID
            String htmlForPage = "";
            ArrayList<Game> gamesOnTable = table.getGamesOnTable();
            System.out.println("SIZE: " + gamesOnTable.size());
            for(int i = 0; i < gamesOnTable.size(); i++){
                //System.out.println("Game :" + i);
                //ArrayList<String> gameStats = db.getGameStats(gamesOnTable.get(i).) ;
                htmlForPage = htmlForPage +
                        "<button  onclick ='clearVals' style=\"margin-top:15px;\" class=\"btn btn-primary\" type=\"submit\" name = \"gamePressed\" data-status=\""+ gamesOnTable.get(i).getStatus() +"\" value = \"" + gamesOnTable.get(i).getGID() + "\">Game " + gamesOnTable.get(i).getTimeOfCreation() + "</button>"
                        + "<br/>" ;

            }
            //System.out.println(htmlForPage);
            req.setAttribute("gameButtons", htmlForPage);
        }catch(Exception e){

        }
    }
}