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
import java.util.ArrayList;
import java.util.Arrays;

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

				resp.sendRedirect(req.getContextPath() + "/dashboard");
				req.getSession().setAttribute("gid", gid);
			}
			else{
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
			req.setAttribute("p1", "Waiting..." );
		}else{
			req.setAttribute("p1", game.getPlayer1().getName());
		}

		if(game.getPlayer2().UID == -1){
			req.setAttribute("p2", "Waiting..." );
		}else{
			req.setAttribute("p2", game.getPlayer2().getName());
		}

		if(game.getPlayer3().UID == -1){
			req.setAttribute("p3", "Waiting..." );
		}else{
			req.setAttribute("p3", game.getPlayer3().getName());
		}

		if(game.getPlayer4().UID == -1){
			req.setAttribute("p4", "Waiting..." );
		}else{
			req.setAttribute("p4", game.getPlayer4().getName());
		}


	}

	public void postData(HttpServletResponse resp) throws IOException{
		Account account = new Account();
		account.populateAccountData(Integer.parseInt(uid));
		Player player = new Player(account.getUsername());

		Table table = new Table(Integer.parseInt(tid));

		Game game = new Game(Integer.parseInt(gid), table.getTID());
		String data = new String();

		if(game.getPlayer1().UID == -1){
			data = data + "Waiting..." + ",";
		}else{
			data = data + game.getPlayer1().getName() + ",";
		}

		if(game.getPlayer2().UID == -1){
			data = data + "Waiting..." + ",";
		}else{
			data = data + game.getPlayer2().getName() + ",";
		}

		if(game.getPlayer3().UID == -1){
			data = data + "Waiting..." + ",";
		}else{
			data = data + game.getPlayer3().getName() + ",";
		}

		if(game.getPlayer4().UID == -1){
			data = data + "Waiting..." + ",";
		}else{
			data = data + game.getPlayer4().getName() + ",";
		}

		resp.setContentType("text/plain");
		resp.getWriter().println(data);
	}
}
