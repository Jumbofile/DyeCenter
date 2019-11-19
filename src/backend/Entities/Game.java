package backend.Entities;

import backend.Database.GameQuery;
import backend.Database.PlayerQuery;

import java.sql.Array;
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
	//winningteam
	private Player[] winningTeam;
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
	private String hash;
	private GameQuery db;

	public Game(){
		db = new GameQuery();
	}
	public Game(int GID, int TID){
		db = new GameQuery();
		team1 = new Player[2];
		team2 = new Player[2];
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
			hash = db.getHash(GID);
			//timestamp
			timeOfCreation = db.getTimestamp(GID);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		player1 = team1[0];
		player2 = team1[1];
		player3 = team2[0];
		player4 = team2[1];
	}
	public void endGame(){
		//todo we need to choose a winner and end the game
		if(status == 0) {
			try {
				Player[] winners = new Player[2];
				Player[] losers = new Player[2];
				PlayerQuery pq = new PlayerQuery();
				if (team1Score > team2Score) {
					winningTeam = team1;
					winners = team1;
					losers = team2;
					pq.setWinners(winners[0]);
					pq.setWinners(winners[1]);
					pq.setLosers(losers[0]);
					pq.setLosers(losers[1]);
					db.setGameStatus(GID, 1);
				} else if(team1Score < team2Score) {
					winningTeam = team2;
					winners = team2;
					losers = team1;
					pq.setWinners(winners[0]);
					pq.setWinners(winners[1]);
					pq.setLosers(losers[0]);
					pq.setLosers(losers[1]);
					db.setGameStatus(GID, 1);
				}else{
					pq.setLosers(player1);
					pq.setLosers(player2);
					pq.setLosers(player3);
					pq.setLosers(player4);
					db.setGameStatus(GID, 1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//todo if status = 1 and winningteam = null its a draw
	}
	public void updatePlayerScore(String player, int value){
		if(player.equals("t1p1Card")){
			player1Score = player1Score + value;
			team1Score = team1Score + value;
		}
		if(player.equals("t1p2Card")){
			player2Score = player2Score + value;
			team1Score = team1Score + value;
		}
		if(player.equals("t2p1Card")){
			player3Score = player3Score + value;
			team2Score = team2Score + value;
		}
		if(player.equals("t2p2Card")){
			player4Score = player4Score + value;
			team2Score = team2Score + value;
		}
	}

	public void updatePlayerPlunk(String player, int value){
		if(player.equals("t1p1Card")){
			player1Plunks = player1Plunks + value;
		}
		if(player.equals("t1p2Card")){
			player2Plunks = player2Plunks + value;
		}
		if(player.equals("t2p1Card")){
			player3Plunks = player3Plunks + value;
		}
		if(player.equals("t2p2Card")){
			player4Plunks = player4Plunks + value;
		}
	}
	public ArrayList<Player> getListOfPlayers(){
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);

		return playerList;
	}

	public boolean updateGameScore(Game game){
		boolean success = false;
		 Game updatedGame;
		//database call to update the game
		try {
			updatedGame = db.updateGame(GID, game);
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}
	public int getGIDFromHash(String hash){
		int gid = -1;
		try {
			gid = db.getGIDFromHash(hash);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gid;
	}
	//getters
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

	public int getGID() {
		return GID;
	}

	//setters
	public void setPlayer1(Player player) {
		this.player1 = player;
	}

	public void setPlayer2(Player player) {
		this.player2 = player;
	}

	public void setPlayer3(Player player) {
		this.player3 = player;
	}

	public void setPlayer4(Player player) {
		this.player4 = player;
	}


	public void setPlayer1Score(int score){
		player1Score = score;
	}

	public void setPlayer2Score(int score){
		player2Score = score;
	}

	public void setPlayer3Score(int score){
		player3Score = score;
	}

	public void setPlayer4Score(int score){
		player4Score = score;
	}

	public void setPlayer1Plunks(int plunks){
		player1Plunks = plunks;
	}

	public void setPlayer2Plunks(int plunks){
		player2Plunks = plunks;
	}

	public void setPlayer3Plunks(int plunks){
		player3Plunks = plunks;
	}

	public void setPlayer4Plunks(int plunks){
		player4Plunks = plunks;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public void setTeam1Score(int team1Score) {
		this.team1Score = team1Score;
	}

	public void setTeam2Score(int team2Score) {
		this.team2Score = team2Score;
	}

	public String getTimeOfCreation() {
		return timeOfCreation;
	}

	public String getHash() {
		return hash;
	}
}
