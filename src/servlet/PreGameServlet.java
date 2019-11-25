package servlet;

import backend.Entities.Account;
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
import java.util.HashMap;
import java.util.Map;
import javax.json.*;

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
			System.out.println(gid);
			System.out.println(tid);
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
			if(playerIDs.contains(Integer.parseInt(uid))){

			}else{
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
				if(teamOpen == 1){
					team1String = team1String.replaceFirst("-1", uid);
				}else{
					team2String = team2String.replaceFirst("-1", uid);
				}

				//add the new player to the table
				game.setTeams(team1String, team2String);

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

				Game game = new Game(Integer.parseInt(gid), Integer.parseInt(tid));
				Util util = new Util();

				ArrayList<String> team1 = new ArrayList<>() ;
				ArrayList<String> team2 = new ArrayList<>() ;

				for(int i = 0; i < teamSelect.length; i++) {
					String[] playerSelectData = teamSelect[i].split(",");
					if(playerSelectData[0].equals("team1")) {
						if(playerSelectData[1].indexOf("guest") == 0) {
							team1.add("-1");
						}
						else {
							team1.add(playerSelectData[1]);
						}
					}

					else if(playerSelectData[0].equals("team2")) {
						if(playerSelectData[1].indexOf("guest") == 0) {
							team2.add("-1");
						}
						else {
							team2.add(playerSelectData[1]);
						}
					}
				}

				while (team1.size() < 2) {
					team1.add("-1") ;
				}

				while (team2.size() < 2) {
					team2.add("-1") ;
				}


				for(int i = 0; i < team1.size(); i++) {
					if(!team1.get(i).equals("-1")){
						Player p = new Player(team1.get(i));
						team1.set(i, p.UID + "");
					}
				}

				for(int i = 0; i < team2.size(); i++) {
					if(!team2.get(i).equals("-1")){
						Player p = new Player(team2.get(i));
						team2.set(i, p.UID + "");
					}
				}

				// As UID's, -1 == guest
				String team1String = team1.get(0) + "," + team1.get(1) ;
				String team2String = team2.get(0) + "," + team2.get(1) ;

				System.out.println("Team strings: " + team1String + "," + team2String);

//				game.setTeams(team1String, team2String);

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
			req.setAttribute("p1User", "guest1" );
		}else{
			req.setAttribute("p1Name", game.getPlayer1().getName());
			req.setAttribute("p1User", game.getPlayer1().getUsername());

		}

		if(game.getPlayer2().UID == -1){
			req.setAttribute("p2Name", "Waiting..." );
			req.setAttribute("p2User", "guest2" );
		}else{
			req.setAttribute("p2Name", game.getPlayer2().getName());
			req.setAttribute("p2User", game.getPlayer2().getUsername());
		}

		if(game.getPlayer3().UID == -1){
			req.setAttribute("p3Name", "Waiting..." );
			req.setAttribute("p3User", "guest3" );
		}else{
			req.setAttribute("p3Name", game.getPlayer3().getName());
			req.setAttribute("p3User", game.getPlayer3().getUsername());
		}

		if(game.getPlayer4().UID == -1){
			req.setAttribute("p4Name", "Waiting..." );
			req.setAttribute("p4User", "guest4" );
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


		String p1Name ;
		String p1User ;

		String p2Name ;
		String p2User ;

		String p3Name ;
		String p3User ;

		String p4Name ;
		String p4User ;

		if(game.getPlayer1().UID == -1){
			p1Name = "Waiting...";
			p1User = "guest1" ;
		}else{
			p1Name = game.getPlayer1().getName() ;
			p1User = game.getPlayer1().getUsername() ;
		}

		if(game.getPlayer2().UID == -1){
			p2Name = "Waiting...";
			p2User = "guest2" ;
		}else{
			p2Name = game.getPlayer2().getName() ;
			p2User = game.getPlayer2().getUsername() ;
		}

		if(game.getPlayer3().UID == -1){
			p3Name = "Waiting...";
			p3User = "guest3" ;
		}else{
			p3Name = game.getPlayer3().getName() ;
			p3User = game.getPlayer3().getUsername() ;
		}

		if(game.getPlayer4().UID == -1){
			p4Name = "Waiting...";
			p4User = "guest4" ;
		}else{
			p4Name = game.getPlayer4().getName() ;
			p4User = game.getPlayer4().getUsername() ;
		}



		try {
			Map<String, Object> config = new HashMap<String, Object>();
			JsonBuilderFactory factory = Json.createBuilderFactory(config);

			JsonObject value = factory.createObjectBuilder()
					.add("p1", factory.createObjectBuilder()
							.add("name", p1Name)
							.add("user", p1User)
					)

					.add("p2", factory.createObjectBuilder()
							.add("name", p2Name)
							.add("user", p2User)
					)

					.add("p3", factory.createObjectBuilder()
							.add("name", p3Name)
							.add("user", p3User)
					)
					.add("p4", factory.createObjectBuilder()
							.add("name", p4Name)
							.add("user", p4User)
					)
					.build();

			resp.setContentType("json");
			resp.getWriter().println(value);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
