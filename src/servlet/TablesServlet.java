package servlet;

import backend.DatabaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TablesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private DatabaseController db = new DatabaseController();
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
            if(req.getParameter("t1").equals("") || req.getParameter("t2").equals("")){
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
                if(req.getParameter("gamePressed") == null) {
                    if(req.getParameter("t1").equals("") || req.getParameter("t2").equals("")){
                        //DO NOTHING!!!
                    }else {
                        //The user pressed the submit button so we can make a game
                        String[] t1 = req.getParameter("t1").split(",");
                        String[] t2 = req.getParameter("t2").split(",");

                        //add players to game
                        ArrayList<String> team1 = new ArrayList<String>();
                        team1.add(t1[0] + "~0");
                        team1.add(t1[1] + "~0");
                        ArrayList<String> team2 = new ArrayList<String>();
                        team2.add(t2[0] + "~0");
                        team2.add(t2[1] + "~0");
                        //create game
                        try {
                            //Try to hit the database
                            String gid = db.createGame(Integer.parseInt(tableID), team1, team2);
                            if (gid != "-1") {
                                resp.sendRedirect(req.getContextPath() + "/game");
                                req.getSession().setAttribute("gid", gid);
                            } else {
                                //Failed to create the game, keeps user on the table view
                                resp.sendRedirect(req.getContextPath() + "/table");
                                req.getSession().setAttribute("tableID", tableID);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Game create fail.");
                        }
                    }
                }else{
                    //They actually hit a game button so just go to that game
                    resp.sendRedirect(req.getContextPath() + "/game");
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
        System.out.println(tableID);
        try{
            //get the tables from your username and display them if the exist
            //pass in GId based on TID
            String htmlForPage = "";
            ArrayList<Integer> gamesOnTable = db.getGameIDs(Integer.parseInt(tableID));
            for(int i = 0; i < gamesOnTable.size(); i++){
                //System.out.println("Game :" + i);
                ArrayList<String> gameStats = db.getGameStats(gamesOnTable.get(i)) ;
                htmlForPage = htmlForPage +
                        "<button  onclick ='clearVals' style=\"margin-top:15px;\" class=\"btn btn-primary\" type=\"submit\" name = \"gamePressed\" data-status=\""+ gameStats.get(4) +"\" value = \"" + gamesOnTable.get(i) + "\">Game " + gameStats.get(5) + "</button>"
                        + "<br/>" ;

            }
            //System.out.println(htmlForPage);
            req.setAttribute("gameButtons", htmlForPage);
        }catch(Exception e){

        }
    }
}