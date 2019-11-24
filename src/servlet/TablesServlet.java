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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
            String tableID = new String();
            try {
                tableID = (String) req.getSession().getAttribute("tableID");
            }catch (NullPointerException e){
                req.getRequestDispatcher("/_view/dashboard.jsp").forward(req, resp);
            }
            //System.out.println("get: " + tableID);

            getGameButton(req, resp);

            req.setAttribute("tableID", tableID);
            req.getSession().setAttribute("tid", tableID);
            req.getRequestDispatcher("/_view/tables.jsp").forward(req, resp);
        }
    }

    //todo - when the game is complete bring to game complete view not game view
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
            getGameButton(req, resp);

            String loadGame = null;

            //Check if the input boxes are empty
            if(req.getParameter("t1p1").equals("")){
                try {
                    loadGame = req.getParameter("gamePressed");
                }catch (Exception e){
                    loadGame = null;
                }
            }

            //the input boxes are empty, the user must of clicked a game button
            if(loadGame != null){

                //Game pressed below
                Game game = new Game(Integer.parseInt(loadGame), Integer.parseInt(tableID));
                if (game.getStatus() == 1) {
                   // resp.sendRedirect(req.getContextPath() + "/view");
                    resp.sendRedirect(req.getContextPath() + "/view");
                    req.getSession().setAttribute("tid", tableID);
                    req.getSession().setAttribute("gid", loadGame);
                }else if(game.getStatus()== -1){
                   // resp.sendRedirect(req.getContextPath() + "/pregame");
                    resp.sendRedirect(req.getContextPath() + "/pregame");
                    req.getSession().setAttribute("tid", tableID);
                    req.getSession().setAttribute("gid", loadGame);
                }else if(game.getStatus()== 0){
                   // resp.sendRedirect(req.getContextPath() + "/game");
                    resp.sendRedirect(req.getContextPath() + "/game");
                    req.getSession().setAttribute("tid", tableID);
                    req.getSession().setAttribute("gid", loadGame);
                }

            }else{
                //The value boxes are empty, users possibly clicked the submit button
                //We dont know if the submit was pressed so we will check if they hit a game button
                //- The game button will be pressed if the "Gamepressed" para isnt null!
                Table table = new Table(Integer.parseInt(tableID));
                if(req.getParameter("gamePressed") == null) {
                    if(req.getParameter("t1p1").equals("") || req.getParameter("t1p2").equals("")
                    ||req.getParameter("t2p1").equals("") || req.getParameter("t2p2").equals("")){
                        if(!(req.getParameter("t1p1").equals(""))) {
                            //goto pre game

                            String players = new String();
                            if (!(req.getParameter("t1p1").equals(""))) {
                                players = players + req.getParameter("t1p1") + ",";
                            }
                            if (!(req.getParameter("t1p2").equals(""))) {
                                players = players + req.getParameter("t1p2") + ",";
                            }
                            if (!(req.getParameter("t2p1").equals(""))) {
                                players = players + req.getParameter("t2p1") + ",";
                            }
                            if (!(req.getParameter("t2p2").equals(""))) {
                                players = players + req.getParameter("t2p1") + ",";
                            }
                            if (players.substring(players.length() - 1).equals(",")) {
                                players = players.substring(0, players.length() - 1);
                            }
                            System.out.println(players);

                            String[] playersArr = players.split(",");
                            ArrayList<Integer> playersInGame = new ArrayList<Integer>();

                            //get the uids of the players
                            for (String username : playersArr) {
                                playersInGame.add(new Player(username.toLowerCase()).UID);
                            }

                            //fill in empty spaces with -1
                            for (int i = playersInGame.size() - 1; i < 4; i++) {
                                playersInGame.add(-1);
                            }

                            Game game = table.createGameWithIDs(playersInGame.get(0), playersInGame.get(1), playersInGame.get(2), playersInGame.get(3));
                            game.setStatus(-1);
                            resp.sendRedirect(req.getContextPath() + "/pregame");
                            req.getSession().setAttribute("gid", game.getGID());
                            req.getSession().setAttribute("tid", tableID);
                        }
                    }else {
                        //The user pressed the submit button so we can make a game

						//get usernames and assign them to a team
						Player[] t1 = new Player[2];
						Player[] t2 = new Player[2];

						try {
                            //populate team 1
                            t1[0] = new Player(req.getParameter("t1p1").toLowerCase());
                            t1[1] = new Player(req.getParameter("t1p2").toLowerCase());

                            //populate team 2
                            t2[0] = new Player(req.getParameter("t2p1").toLowerCase());
                            t2[1] = new Player(req.getParameter("t2p2").toLowerCase());
                        }catch (Exception e){
						    //that usersname doesnt exist
                            resp.sendRedirect(req.getContextPath() + "/table");
                        }
                        Game game = null;
                        if(playersUnique(t1, t2)) {


                            //create game
                            game = table.createGame(t1, t2);
                        }
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
                    Game gameRedirect = new Game(Integer.parseInt(req.getParameter("gamePressed")),Integer.parseInt(tableID));
                    if(gameRedirect.getStatus() == 1){
                        resp.sendRedirect(req.getContextPath() + "/view");
                    }else{
                        resp.sendRedirect(req.getContextPath() + "/game");
                    }
                    //resp.sendRedirect(req.getContextPath() + "/game");
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

    private void getGameButton(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        //int tableID = (int)req.getAttribute("tableId");
        String tableID = (String)req.getSession().getAttribute("tableID");
        System.out.println("TABLE ID: " + tableID);
        int tid = -1;
        try {
            tid = Integer.parseInt(tableID);
        }catch(NumberFormatException e){
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        }
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

                ArrayList<Integer> scores = new ArrayList<>();
                scores.add(gamesOnTable.get(i).getPlayer1Score());
                scores.add(gamesOnTable.get(i).getPlayer2Score());
                scores.add(gamesOnTable.get(i).getPlayer3Score());
                scores.add(gamesOnTable.get(i).getPlayer4Score());

                ArrayList<Integer> plunks = new ArrayList<>();
                plunks.add(gamesOnTable.get(i).getPlayer1Plunks()) ;
                plunks.add(gamesOnTable.get(i).getPlayer2Plunks()) ;
                plunks.add(gamesOnTable.get(i).getPlayer3Plunks()) ;
                plunks.add(gamesOnTable.get(i).getPlayer4Plunks()) ;

                ArrayList<String> usernames = new ArrayList<>();
                usernames.add(gamesOnTable.get(i).getPlayer1().getUsername());
                usernames.add(gamesOnTable.get(i).getPlayer2().getUsername());
                usernames.add(gamesOnTable.get(i).getPlayer3().getUsername());
                usernames.add(gamesOnTable.get(i).getPlayer4().getUsername());

                ArrayList<String> names = new ArrayList<>();
                names.add(gamesOnTable.get(i).getPlayer1().getName());
                names.add(gamesOnTable.get(i).getPlayer2().getName());
                names.add(gamesOnTable.get(i).getPlayer3().getName());
                names.add(gamesOnTable.get(i).getPlayer4().getName());

                String scoreString = scores.toString().substring(1, scores.toString().length()-1) ;
                String plunkString = plunks.toString().substring(1, plunks.toString().length()-1);
                String usernameString = usernames.toString().substring(1, usernames.toString().length()-1) ;
                String nameString = names.toString().substring(1, names.toString().length()-1) ;

                htmlForPage = htmlForPage +
                        "<button  onclick ='clearVals' class=\"game-card\" type=\"submit\" name = \"gamePressed\" data-hash=\""+ gamesOnTable.get(i).getHash() +"\" data-leader=\"" + gamesOnTable.get(i).getGameWinner() + "\" data-status=\""+ gamesOnTable.get(i).getStatus() + "\" "+
                        "data-time=\""+ gamesOnTable.get(i).getTimeOfCreation() + "\" data-scores = \""+ scoreString +"\" data-plunks=\""+ plunkString + "\" "+
                        "data-usernames=\""+ usernameString +"\" data-names=\""+ nameString +"\" value = \""+ gamesOnTable.get(i).getGID() +"\">" +
                            "<div class='game-card-cont'>" +
                                "<div class='game-grid-item game-card-header'>header</div>"+

                                "<div class='t1 game-grid-item team-score' id='team1-score'></div>"+
                                "<div class='t1 game-grid-item player p1'>" +
                                    "<span class='player-name' id='p1-name'></span>" +
                                        "<div class='player-stats'>" +
                                            "<span class='stat' id='p1-points'></span>" +
                                            "<span class='stat' id='p1-plunks'></span>" +
                                        "</div>" +
                                "</div>"+

                                "<div class='t1 game-grid-item player p2'>" +
                                    "<span class='player-name' id='p2-name'></span>" +
                                    "<div class='player-stats'>" +
                                        "<span class='stat' id='p2-points'></span>" +
                                        "<span class='stat' id='p2-plunks'></span>" +
                                    "</div>" +
                                "</div>"+

                                "<div class='t2 game-grid-item team-score' id='team2-score'></div>"+
                                "<div class='t2 game-grid-item player p3'>" +
                                    "<span class='player-name' id='p3-name'></span>" +
                                    "<div class='player-stats'>" +
                                        "<span class='stat' id='p3-points'></span>" +
                                        "<span class='stat' id='p3-plunks'></span>" +
                                    "</div>" +
                                "</div>"+

                                "<div class='t2 game-grid-item player p4'>" +
                                    "<span class='player-name' id='p4-name'></span>" +
                                    "<div class='player-stats'>" +
                                        "<span class='stat' id='p4-points'></span>" +
                                        "<span class='stat' id='p4-plunks'></span>" +
                                    "</div>" +
                                "</div>"+
                            "</div>"+
                        "</button>" +
                        "<br/>" ;
            }
            //System.out.println(htmlForPage);
            req.setAttribute("gameButtons", htmlForPage);
        }catch(Exception e){

        }
    }

    public boolean playersUnique(Player[] t1, Player[] t2){
        boolean result = true;
        //we have to make sure all of the usernames are unique
        //t1p1 and t1p2
        if(t1[0].getUsername().equals(t1[1].getUsername())){
            result = false;
        }

        //t2p1 and t2p2
        if(t2[0].getUsername().equals(t2[1].getUsername())){
            result = false;
        }

        //t1p1 t2p1
        if(t1[0].getUsername().equals(t2[0].getUsername())){
            result = false;
        }

        //t1p1 t2p2
        if(t1[0].getUsername().equals(t2[1].getUsername())){
            result = false;
        }

        //t1p2 t2p1
        if(t1[1].getUsername().equals(t2[0].getUsername())){
            result = false;
        }

        //t1p2 t2p2
        if(t1[1].getUsername().equals(t2[1].getUsername())){
            result = false;
        }

        return result;
    }
}