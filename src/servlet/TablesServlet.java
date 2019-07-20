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
            System.out.println("get: " + tableID);

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
            System.out.println(tableID);

            //get game buttons
            getGameButton(req);

            String loadGame = req.getParameter("gamePressed");
            //System.out.println("YEET" + req.getParameter("t1"));
            if(req.getParameter("t1").equals("") || req.getParameter("t2").equals("")){
                //Game pressed below
                resp.sendRedirect(req.getContextPath() + "/game");
                req.getSession().setAttribute("gid", loadGame);


            }else {
                //Create game funtions below
                String[] t1 = req.getParameter("t1").split(",");
                String[] t2 = req.getParameter("t2").split(",");

                //add players to game
                ArrayList<String> team1 = new ArrayList<String>();
                team1.add(t1[0]+"~0");
                team1.add(t1[1]+"~0");
                ArrayList<String> team2 = new ArrayList<String>();
                team2.add(t2[0]+"~0");
                team2.add(t2[1]+"~0");
                //create game
                try {
                    String gid = db.createGame(Integer.parseInt(tableID), team1, team2);
                    if(gid != "-1") {
                        resp.sendRedirect(req.getContextPath() + "/game");
                        req.getSession().setAttribute("gid", gid);
                    }
                    else {
                        //Failed to create the game, keeps user on the table view
                        resp.sendRedirect(req.getContextPath() + "/table");
                        req.getSession().setAttribute("tableID", tableID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Game create fail.");
                }
            }
           // req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
//            resp.sendRedirect(req.getContextPath() + "/table");
//            req.getSession().setAttribute("tableID", tableID);
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
                        "<button style=\"margin-top:15px;\" class=\"gameBtn btn btn-primary\" type=\"submit\" name = \"gamePressed\" data-status=\""+ gameStats.get(4) +"\" value = \"" + gamesOnTable.get(i) + "\">Game " + gameStats.get(5) + "</button>"
                        + "<br/>" ;

            }
            //System.out.println(htmlForPage);
            req.setAttribute("gameButtons", htmlForPage);
        }catch(Exception e){

        }
    }
}