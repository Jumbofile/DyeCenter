package backend.Entities;

import backend.Database.GameQuery;
import backend.Database.PlayerQuery;

import java.sql.SQLException;
import java.util.ArrayList;

public class Player {
	public int UID;
	private String username;
	private String name;
	private int type;
	private int points;
	private int plunks;
	private int wins;
	private int loss;
	private PlayerQuery db;

	public Player() {
		db = new PlayerQuery();
	}

	public Player(String username){
		//database controller instance
		db = new PlayerQuery();
		if(!username.equals("<<empty>>")) {
			this.username = username;
			//database actions
			try {
				//get account tied to username
				Account account = new Account();
				UID = account.getUID(username);
				account.populateAccountData(UID);
				type = account.getType();
				name = account.getName();
				//update stats
				ArrayList<Integer> stats = db.getUserStats(UID);
				points = stats.get(0);
				plunks = stats.get(1);
				wins = stats.get(2);
				loss = stats.get(3);
			} catch (SQLException e) {
				System.out.println("Error setting player Object.");
				e.printStackTrace();
			}
		}
		else {
			UID = -1;
			type = -1;
			name = "Waiting..." ;
			this.username = "<<empty>>" ;
			points = 0;
			plunks = 0;
			wins = 0;
			loss = 0;
		}

	}
	public int getTotalGames(){
		return wins + loss;
	}

	public String getUsername() {
		return username;
	}

	public int getUID() {return this.UID ;}

	public int getType() {
		return type;
	}

	public int getPoints() {
		return points;
	}

	public int getPlunks() {
		return plunks;
	}

	public int getWins() {
		return wins;
	}

	public int getLoss() {
		return loss;
	}

	public void updatePoints(int points, int plunks){
		if(getUID() != -1) {
			try {
				db.updateUserPoints(points, UID);
				db.updateUserPlunks(plunks, UID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public float getWinLossRatio(){
		float ratio = -1;
		float total = wins + loss;
		if(wins == 0){
			if(loss == 0){
				ratio = 0.0f;
			}
			ratio = 0.0f;
		}else{
			ratio = wins/total;
		}
		ratio = Math.round(ratio * 100.0f)*100.0f / 100.0f;
		return ratio;
	}

	public String getName() {
		return name;
	}
}
