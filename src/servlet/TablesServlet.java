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
			//req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
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
            try{
                //get the tables from your username and display them if the exist
                //pass in GId based on TID
                ArrayList<Integer> gamesOnTable = db.getGames(Integer.parseInt(tableID));
                for(Integer id : gamesOnTable){
                    System.out.println(id);
                }
            }catch(Exception e){

            }
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
                db.createGame(Integer.parseInt(tableID), team1, team2);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Game create fail.");
            }

           // req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            req.setAttribute("tableID", tableID);
            req.getRequestDispatcher("/_view/table.jsp").forward(req, resp);
        }

    }
}