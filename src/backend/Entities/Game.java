package backend.Entities;

import backend.Database.GameQuery;

import java.sql.SQLException;
import java.util.ArrayList;

public class Game {
	private int GID;
	private int TID;
	//players
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	//teams
	private Player[] team1;
	private Player[] team2;
	//scores
	private int team1Score;
	private int team2Score;
	//points
	private int player1Score;
	private int player2Score;
	private int player3Score;
	private int player4Score;
	//plunks
	private int player1Plunks;
	private int player2Plunks;
	private int player3Plunks;
	private int player4Plunks;
	//status
	private int status;
	//timestamp
	private String timeOfCreation;
	private GameQuery db;

	public Game(int GID, int TID){
		db = new GameQuery();
		this.GID = GID;
		this.TID = TID;
		try {
			ArrayList<Player> players = db.getPlayersFromGame(GID);
			team1[0] = players.get(0);
			team1[1] = players.get(1);
			team2[0] = players.get(2);
			team2[1] = players.get(3);

			//scores
			ArrayList<Integer> stats =  db.getGameStats(GID);

			//team scores
			team1Score = stats.get(0);
			team2Score = stats.get(1);

			//player scores
			player1Score = stats.get(2);
			player2Score = stats.get(3);
			player3Score = stats.get(4);
			player4Score = stats.get(5);

			//player plunks
			player1Plunks = stats.get(6);
			player2Plunks = stats.get(7);
			player3Plunks = stats.get(8);
			player4Plunks = stats.get(9);

			//status
			status = stats.get(10);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		player1 = team1[0];
		player2 = team1[1];
		player3 = team2[0];
		player4 = team2[1];



	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Player getPlayer3() {
		return player3;
	}

	public Player getPlayer4() {
		return player4;
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public int getPlayer3Score() {
		return player3Score;
	}

	public int getPlayer4Score() {
		return player4Score;
	}

	public int getPlayer1Plunks() {
		return player1Plunks;
	}

	public int getPlayer2Plunks() {
		return player2Plunks;
	}

	public int getPlayer3Plunks() {
		return player3Plunks;
	}

	public int getPlayer4Plunks() {
		return player4Plunks;
	}

	public int getStatus() {
		return status;
	}

	public int getTeam2Score() {
		return team2Score;
	}

	public int getTeam1Score() {
		return team1Score;
	}
}
