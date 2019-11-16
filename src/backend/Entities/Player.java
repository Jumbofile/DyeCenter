package backend.Entities;

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

	public Player(String username){
		//database controller instance
		db = new PlayerQuery();
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
		}catch (SQLException e){
			System.out.println("Error setting player Object.");
			e.printStackTrace();
		}

	}
	public int getTotalGames(){
		return wins + loss;
	}

	public String getUsername() {
		return username;
	}

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

	public float getWinLossRatio(){
		float ratio = -1;
		if(loss ==0){
			if(wins == 0){
				ratio = 0;
			}else{
				ratio = (float)wins / 1.0f;
			}
		}else{
			ratio = (float)wins /(float)loss;
		}
		return ratio;
	}

	public String getName() {
		return name;
	}
}
