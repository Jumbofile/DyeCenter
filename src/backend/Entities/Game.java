package backend.Entities;

import backend.Database.GameQuery;

import java.sql.SQLException;

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
	private int status;
	//timestamp
	private String timeOfCreation;
	private GameQuery db;

	public Game(int GID, int TID){
		db = new GameQuery();
		this.GID = GID;
		this.TID = TID;

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
}
