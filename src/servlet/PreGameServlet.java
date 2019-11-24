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

			if(playerIDs.contains(Integer.parseInt(uid))){
				req.getSession().setAttribute("gid", gid);
				req.getSession().setAttribute("uid", uid);
				req.getRequestDispatcher("/_view/pregame.jsp").forward(req, resp);
			}else{
				ArrayList<String> teamIds = game.getTeams();
				String team1String = teamIds.get(0);
				String team2String = teamIds.get(1);
				String[] team1 = team1String.split(",");
				String[] team2 = team2String.split(",");
				int teamOpen = -1;

				for(String ids: team1){
					if(Integer.parseInt(ids) == -1){
						teamOpen = 1;
					}
				}

				for(String ids: team2){
					if(Integer.parseInt(ids) == -1){
						teamOpen = 2;
					}
				}

				if(teamOpen == 1){
					team1String.replace("-1", uid);
				}else{
					team2String.replace("-1", uid);
				}
				getData(req, resp);
			}
		}try {
			req.getSession().setAttribute("gid", gid);
			req.getSession().setAttribute("uid", uid);
			req.getRequestDispatcher("/_view/pregame.jsp").forward(req, resp);
		}catch (Exception e){
			//should really try to name this exception or possible remove it
			e.printStackTrace();
		}
	}

	//todo - dont allow score change when game status = 1
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		uid = (String) req.getSession().getAttribute("uid"); //session stuff
		gid = (String) req.getSession().getAttribute("gid");
		System.out.println("View DoPost");

		if (uid == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
		}else {
			//getData(req, resp);
			postData(resp);
			//resp.sendRedirect(req.getContextPath() + "/view");
			req.getSession().setAttribute("gid", gid);
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
	}

	public void postData(HttpServletResponse resp) throws IOException{
		Account account = new Account();
		account.populateAccountData(Integer.parseInt(uid));
		Player player = new Player(account.getUsername());

		Table table = new Table(Integer.parseInt(tid));

		Game game = new Game(Integer.parseInt(gid), table.getTID());
		String data = new String();

		resp.setContentType("text/plain");
		resp.getWriter().println(data);
	}
}
