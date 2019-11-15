package backend.Entities;

import backend.Database.TableQuery;

import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
	private int TID;
	private String name;
	private int ownerUID;
	private int plunkAmount;
	private ArrayList<Player> playersOnTable;
	private ArrayList<Game> gamesOnTable;
	private TableQuery db;

	public Table(){
		db = new TableQuery();
		gamesOnTable = new ArrayList<Game>();
	}

	public Table(int TID){
		db = new TableQuery();
		try {
			System.out.println(TID);
			//Table tableToReturn = new Table();
			Table tableToReturn = db.getTableBasedOnID(TID);
			this.TID = tableToReturn.TID;
			name = tableToReturn.name;
			ownerUID = tableToReturn.ownerUID;
			plunkAmount = tableToReturn.plunkAmount;
			playersOnTable = tableToReturn.playersOnTable;
			gamesOnTable = tableToReturn.gamesOnTable;
		} catch (SQLException e) {
			System.out.println("IT FAILED HERE");
			e.printStackTrace();
		}
		gamesOnTable = new ArrayList<Game>();
	}

	public Table createTable(String name, int UID, int plunkValue){
		Table table = null;
		try {
			Object[] tableResult = db.createTable(name, UID, plunkValue);
			if((boolean)tableResult[0]){
				table = new Table();
				table.setName(name);
				table.setOwnerUID(UID);
				table.setPlunkAmount(plunkValue);
				table.setTID((int)tableResult[1]);

				ArrayList<Integer> gidOnTable = db.getGameIDs(TID);
				for(int gid : gidOnTable){
					gamesOnTable.add(new Game(gid, TID));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}

	public Table getTable(int tid){
		Table tableToReturn = null;
		try {
			tableToReturn = db.getTableBasedOnID(TID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableToReturn;
	}

	public void setTID(int TID) {
		this.TID = TID;
	}

	public Game createGame(Player[] team1, Player[] team2){

		//todo
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwnerUID(int ownerUID) {
		this.ownerUID = ownerUID;
	}

	public void setPlunkAmount(int plunkAmount) {
		this.plunkAmount = plunkAmount;
	}

	//todo fix this, what happens if there is only one player on the table
	//this should be somewhere else
	public void setPlayersOnTable(ArrayList<Player> playersOnTable) {
		ArrayList<Player> playersOnTheTable = null;
		ArrayList<Player> uniqueEntries = new ArrayList<Player>();
		if(gamesOnTable.size() > 0) {
			for (Game games : gamesOnTable) {
				playersOnTable.add(games.getPlayer1());
				playersOnTable.add(games.getPlayer2());
				playersOnTable.add(games.getPlayer3());
				playersOnTable.add(games.getPlayer4());
			}

			for (Player players : playersOnTable) {
				if (uniqueEntries.contains(players)) {
					//do nothing
				} else {
					uniqueEntries.add(players);
				}
			}

			this.playersOnTable = uniqueEntries;
		}
	}

	public ArrayList<Player> getPlayersOnTable() {
		return playersOnTable;
	}

	public int getPlunkAmount() {
		return plunkAmount;
	}

	public int getOwnerUID() {
		return ownerUID;
	}

	public String getName() {
		return name;
	}

	public int getTID() {
		return TID;
	}

	public ArrayList<Game> getGamesOnTable(){
		return gamesOnTable;
	}

	public void setGamesOnTable(ArrayList<Game> gamesOnTable) {
		this.gamesOnTable = gamesOnTable;
	}
}
