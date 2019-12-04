package servlet;

import backend.Entities.Account;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import javax.json.spi.JsonProvider;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.json.*;

import jdk.nashorn.internal.parser.JSONParser;
import utilities.Util;

public class PreGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String uid = null;
	private String gid = null ;
	private String tid = null ;
	//todo make back button remove you from the game
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("PreGame DoGet");
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

			Game game = new Game(Integer.parseInt(gid), Integer.parseInt(tid));

			ArrayList<Integer> playerIDs = game.returnAllUIDs();

			getData(req, resp);

			if(!playerIDs.contains(Integer.parseInt(uid))){
				ArrayList<String> teamIds = game.getTeams();
				String team1String = teamIds.get(0);
				String team2String = teamIds.get(1);
				String[] team1 = team1String.split(",");
				String[] team2 = team2String.split(",");

				int teamOpen = -1;

				//check each team for a -1 in the roster
				for(String ids: team1){
					if(Integer.parseInt(ids) == -1){
						teamOpen = 1;
					}
				}
				if(teamOpen != 1) {
					for (String ids : team2) {
						if (Integer.parseInt(ids) == -1) {
							teamOpen = 2;
						}
					}
				}

				//based on the team that had a -1, replace the -1 with the uid
				System.out.println("Current UID: " + uid);
				System.out.println("Team Open: " + teamOpen);
				System.out.println("Team 1: " + team1String);
				if(teamOpen == 1){
					team1String = team1String.replaceFirst("-1", uid);
				}else{
					team2String = team2String.replaceFirst("-1", uid);
				}

				System.out.println("Team1String: " + team1String);

				//add the new player to the table
				game.setTeams(team1String, team2String);
				game.updateGame();
				System.out.println("Player1: " + game.getPlayer1().getUsername());

			}
			String gameID;
			gameID = game.getGID() + "";
			req.getRequestDispatcher("/_view/pregame.jsp").forward(req, resp);
			req.getSession().setAttribute("gid", gameID);
			req.getSession().setAttribute("tid", tid);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		uid = (String) req.getSession().getAttribute("uid"); //session stuff
		gid = (String) req.getSession().getAttribute("gid");
		System.out.println("PreGame DoPost");

		if (uid == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
		}else {
			//getData(req, resp);
			String back = req.getParameter("backValue");


			System.out.println("Back: " + back);

			if(back.equals("true")) {
				Game game = new Game(Integer.parseInt(gid), Integer.parseInt(tid));
				ArrayList<String> teamIds = game.getTeams();
				String team1String = teamIds.get(0);
				String team2String = teamIds.get(1);
				String[] team1arr = team1String.split(",");
				String[] team2arr = team2String.split(",");


				if (team1arr[0].equals(uid) || team1arr[1].equals(uid)) {
					team1String = team1String.replaceFirst(uid, "-1");
				}

				if (team2arr[0].equals(uid) || team2arr[1].equals(uid)) {
					team2String = team2String.replaceFirst(uid, "-1");
				}

				game.setTeams(team1String, team2String);
				req.getSession().setAttribute("gid", gid);
			}
			else{
				System.out.println(req.getParameter("teamSelectP1"));
				System.out.println(req.getParameter("teamSelectP2"));
				System.out.println(req.getParameter("teamSelectP3"));
				System.out.println(req.getParameter("teamSelectP4"));

				String[] teamSelect = {req.getParameter("teamSelectP1"), req.getParameter("teamSelectP2"), req.getParameter("teamSelectP3"), req.getParameter("teamSelectP4")};

				System.out.println("Team Select: " + teamSelect[0]);
                System.out.println("Team Select: " + teamSelect[1]);
                System.out.println("Team Select: " + teamSelect[2]);
                System.out.println("Team Select: " + teamSelect[3]);

				Game game = new Game(Integer.parseInt(gid), Integer.parseInt(tid));

				ArrayList<String> team1 = new ArrayList<>() ;
				ArrayList<String> team2 = new ArrayList<>() ;

				for(int i = 0; i < teamSelect.length; i++) {
					String[] playerSelectData = teamSelect[i].split(",");
					if(playerSelectData[0].equals("team1")) {
						if(playerSelectData[1].equals("<<empty>>")) {
							team1.add("<<empty>>");
						}
						else {
							team1.add(playerSelectData[1]);
						}
					}

					else if(playerSelectData[0].equals("team2")) {
						if(playerSelectData[1].equals("<<empty>>")) {
							team2.add("<<empty>>");
						}
						else {
							team2.add(playerSelectData[1]);
						}
					}
				}

				while (team1.size() < 2) {
					team1.add("<<empty>>") ;
				}

				while (team2.size() < 2) {
					team2.add("<<empty>>") ;
				}


				for(int i = 0; i < team1.size(); i++) {
					if(!team1.get(i).equals("<<empty>>")){
						Player p = new Player(team1.get(i));
						team1.set(i, p.UID + "");
					}
				}

				for(int i = 0; i < team2.size(); i++) {
					if(!team2.get(i).equals("<<empty>>")){
						Player p = new Player(team2.get(i));
						team2.set(i, p.UID + "");
					}
				}

				// As UID's, -1 == guest
				String team1String = team1.get(0) + "," + team1.get(1) ;
				String team2String = team2.get(0) + "," + team2.get(1) ;

				System.out.println("Team strings: " + team1String + "," + team2String);

				//game.setTeams(team1String, team2String);

				postData(resp);
				getData(req, resp);
			}
		}
	}

	public void getData(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Account account = new Account();
		account.populateAccountData(Integer.parseInt(uid));
		Player player = new Player(account.getUsername());

		Table table = new Table(Integer.parseInt(tid));

		Game game = new Game(Integer.parseInt(gid), table.getTID());

		//set hash value
		req.setAttribute("gameHash", game.getHash());

		if(game.getPlayer1().UID == -1){
			req.setAttribute("p1Name", "Waiting..." );
			req.setAttribute("p1User", "<<empty>>" );
		}else{
			req.setAttribute("p1Name", game.getPlayer1().getName());
			req.setAttribute("p1User", game.getPlayer1().getUsername());

		}

		if(game.getPlayer2().UID == -1){
			req.setAttribute("p2Name", "Waiting..." );
			req.setAttribute("p2User", "<<empty>>" );
		}else{
			req.setAttribute("p2Name", game.getPlayer2().getName());
			req.setAttribute("p2User", game.getPlayer2().getUsername());
		}

		if(game.getPlayer3().UID == -1){
			req.setAttribute("p3Name", "Waiting..." );
			req.setAttribute("p3User", "<<empty>>" );
		}else{
			req.setAttribute("p3Name", game.getPlayer3().getName());
			req.setAttribute("p3User", game.getPlayer3().getUsername());
		}

		if(game.getPlayer4().UID == -1){
			req.setAttribute("p4Name", "Waiting..." );
			req.setAttribute("p4User", "<<empty>>" );
		}else{
			req.setAttribute("p4Name", game.getPlayer4().getName());
			req.setAttribute("p4User", game.getPlayer4().getUsername());
		}


	}

	public void postData(HttpServletResponse resp) throws IOException{
		Account account = new Account();
		account.populateAccountData(Integer.parseInt(uid));
		Player player = new Player(account.getUsername());

		Table table = new Table(Integer.parseInt(tid));
		Game game = new Game(Integer.parseInt(gid), table.getTID());


		String p1Name = game.getPlayer1().getName();
		String p1User = game.getPlayer1().getUsername();

		String p2Name = game.getPlayer2().getName();
		String p2User = game.getPlayer2().getUsername();

		String p3Name = game.getPlayer3().getName();
		String p3User = game.getPlayer3().getUsername();

		String p4Name = game.getPlayer4().getName();
		String p4User = game.getPlayer4().getUsername();

        String teamString = p1User + "," + p2User + "," + p3User + "," + p4User ;
        System.out.println("Initial usernames: " + teamString);

        Map<String, Object> parseConfig = new HashMap<String, Object>();
		try {
            //generate the JSON based on the uid of the player on the screen
            String tmp_str = game.generateJSON(Integer.parseInt(uid)).toString();

            //DEBUG PRINT
            System.out.println("String from DB: " + tmp_str);

            //Parse throught the json
            if(tmp_str != null) {
				JsonString json = Json.createValue(tmp_str);

				JsonReader jsonReader = Json.createReader(new StringReader(tmp_str));
				JsonObject tempGame = jsonReader.readObject();
				jsonReader.close();

				System.out.println("JsonObject 'tempGame': " + tempGame);

				p1Name = tempGame.getJsonObject("p1").getString("name");
				p1User = tempGame.getJsonObject("p1").getString("user");

				p2Name = tempGame.getJsonObject("p2").getString("name");
				p2User = tempGame.getJsonObject("p2").getString("user");

				p3Name = tempGame.getJsonObject("p3").getString("name");
				p3User = tempGame.getJsonObject("p3").getString("user");

				p4Name = tempGame.getJsonObject("p4").getString("name");
				p4User = tempGame.getJsonObject("p4").getString("user");

				Player player1 = new Player(p1User);
				Player player2 = new Player(p2User);
				Player player3 = new Player(p3User);
				Player player4 = new Player(p4User);

				game.setPlayer1(player1);
				game.setPlayer2(player2);
				game.setPlayer3(player3);
				game.setPlayer4(player4);

				game.updateGame();
				teamString =
						game.getPlayer1().getUsername() + "," +
						game.getPlayer2().getUsername() + "," +
						game.getPlayer3().getUsername() + "," +
						game.getPlayer4().getUsername();
			}

        } catch (Exception e) {
		    e.printStackTrace();
        }

		try {

            JsonObject value = game.generateJSON(Integer.parseInt(uid));

			resp.setContentType("json");
			resp.getWriter().println(value);

			System.out.println(value.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
