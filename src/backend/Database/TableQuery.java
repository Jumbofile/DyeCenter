package backend.Database;

import backend.Entities.Account;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TableQuery extends DatabaseFactory{
	//date
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");



	/***
	 * Returns table name based on id
	 * @param TID
	 * @return
	 * @throws SQLException
	 */
	public String getTableNameBasedOnID(int TID) throws SQLException{
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				String rtnString = null;
				try {
					// retreive username attribute from login
					stmt = conn.prepareStatement("SELECT name from dyetable where TID = ?");
					stmt.setInt(1, TID);
					resultSet = stmt.executeQuery();

					resultSet.next();
				}catch (Exception e){

				}


				rtnString = resultSet.getString("name");

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return rtnString;
			}
		});
	}

	/***
	 * Creates a game based on 2 teams and a TID
	 * @param TID
	 * @param teamOne
	 * @param teamTwo
	 * @return
	 * @throws SQLException
	 */
	//TODO fix this to return a game object, the tid is there, change parameters to hold player[]
	public Game createGame(int TID, Player[] teamOne, Player[] teamTwo) throws SQLException {
		return executeTransaction(new Transaction<Game>() {
			@Override
			public Game execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					//TODO
					//Create a table with the teams listed, there will need to be a method made that allows the players to be changed
					//This needs to allow users to choose teams on the servlet/jsp side
					java.util.Date myDate = new Date();
					String date = sdf.format(myDate);
					//TODO
					String sql = "insert into game(TID, team_1, team_2, score_1, score_2, status, timestamp)values(?, ?, ?, 0, 0, 0, ?)";

					stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					stmt.setInt(1, TID);
					stmt.setString(2, teamOne[0].UID +"," + teamOne[1].UID);
					stmt.setString(3, teamTwo[0].UID +"," + teamTwo[1].UID);
					stmt.setString(4, date);
					stmt.executeUpdate();

					ResultSet rs = stmt.getGeneratedKeys();
					rs.next();
					int gid = rs.getInt(1);


					//System.out.println("Team 1: "+ teamOne.get(0));
					//System.out.println("Team 2: "+ teamTwo.get(0));
					Game game = new Game(TID, gid);

					//populate table
					TableQuery tb = new TableQuery();
					String playerUIDs = game.getPlayer1().UID + "," +
							game.getPlayer2().UID + "," +
							game.getPlayer3().UID + "," +
							game.getPlayer4().UID;
					tb.addPlayersToTable(TID,playerUIDs) ;

					return game;
				} catch (Exception e){
					e.printStackTrace();
					return null;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	public Table getTableBasedOnID(int TID) throws SQLException{
		return executeTransaction(new Transaction<Table>() {
			@Override
			public Table execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				System.out.println("HELLO");

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT name, UID, plunk, players from dyetable where TID = ?");
				stmt.setInt(1, TID);
				resultSet = stmt.executeQuery();

				resultSet.next();

				String name = resultSet.getString("name");
				int UID = resultSet.getInt("UID");
				int plunk = resultSet.getInt("plunk");
				Table rtnTable = new Table();

				rtnTable.setTID(TID);
				rtnTable.setName(name);
				rtnTable.setOwnerUID(UID);
				rtnTable.setPlunkAmount(plunk);
				String playerCSV = resultSet.getString("players");
				System.out.println(playerCSV);
				String[] playerID = playerCSV.split(",");

				ArrayList<Player> playersOnTable = new ArrayList<Player>();
				for(String id : playerID){
					Account account = new Account();
					account.populateAccountData(Integer.parseInt(id));
					playersOnTable.add(account.getPlayerFromAccount());
				}
				rtnTable.setPlayersOnTable(playersOnTable);

				//print stuff
				System.out.println(rtnTable.getTID());
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return rtnTable;
			}
		});
	}

	/***
	 * Creates a table
	 * @param 	tableName
	 * @param plunk
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public Object[] createTable(String tableName, int uid, int plunk) throws SQLException {
		return executeTransaction(new Transaction<Object[]>() {
			@Override
			public Object[] execute(Connection conn) throws SQLException {
				//Connection conn = null;
				/**
				 * Object array has a bool in index 0 and int in index 1
				 */
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				Object[] resultArray = new Object[]{false, 1};
				try {
					stmt = conn.prepareStatement("insert into dyetable(name, UID, plunk, players) values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setString(1, tableName);
					stmt.setInt(2, uid);
					stmt.setInt(3, plunk);
					stmt.setString(4, Integer.toString(uid));

					// execute the query
					stmt.execute();

					//get table key
					ResultSet rs = stmt.getGeneratedKeys();
					rs.next();

					//add stuff to array
					resultArray[0] = true;
					resultArray[1] = rs.getInt(1);

				} catch (Exception e){
					e.printStackTrace();
					resultArray[0] = false;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				return  resultArray;
			}
		});
	}

	/***
	 * Returns an array of game ids based on table id
	 * @param TID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getGameIDs(int TID) throws SQLException{
		return executeTransaction(new Transaction<ArrayList<Integer> >() {
			@Override
			public ArrayList<Integer> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				ArrayList<Integer> rtnStats = new ArrayList<Integer>();

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT GID from game where TID = ?" );
				stmt.setInt( 1, TID);
				resultSet = stmt.executeQuery();

				while(resultSet.next()) {
					//System.out.println("DB :" + resultSet.getInt(1));
					rtnStats.add(resultSet.getInt(1));
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return rtnStats;
			}
		});
	}

	public boolean addPlayersToTable(int tid, String players) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				String[] newPlayers = players.split(",");
				ArrayList<String> newPlayerList = new ArrayList<String>(Arrays.asList(newPlayers)) ;

				String tablePlayers;

				try{
					stmt = conn.prepareStatement("SELECT players from dyetable where tid = ?") ;
					stmt.setInt(1, tid);

					resultSet = stmt.executeQuery();

					resultSet.next();

					tablePlayers = resultSet.getString(1);
					ArrayList<String> tablePlayerList =  new ArrayList<String>(Arrays.asList(tablePlayers.split("\\s*,\\s*")));

					for(int i = 0; i < tablePlayerList.size(); i++){
						newPlayerList.remove(tablePlayerList.get(i)) ;
					}


					if(newPlayerList.isEmpty()){
						//fuck you
					}else{
						tablePlayerList.addAll(newPlayerList);
						String newPlayerString = String.join(",",tablePlayerList) ;

						stmt = conn.prepareStatement("update dyetable set players = ? where tid = ?") ;
						stmt.setString(1,newPlayerString);
						stmt.setInt(2, tid);

						stmt.executeUpdate();
					}

				} catch (Exception e){
					e.printStackTrace();
				}
				//System.out.println("DB val: " + value);
				//System.out.println("DB uid: " + uid);
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				return true;
			}
		});
	}
}
