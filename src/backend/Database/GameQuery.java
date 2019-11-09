package backend.Database;

import backend.Entities.Account;
import backend.Entities.Player;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameQuery extends DatabaseFactory {

	public GameQuery(){

	}
	public String getTableFromGameID(int GID) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				String rtnString = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT TID from games where GID = ?" );
				stmt.setInt( 1, GID);
				resultSet = stmt.executeQuery();

				resultSet.next();

				rtnString = resultSet.getString("name");

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return rtnString;
			}
		});

	}

	public ArrayList<Player> getPlayersFromGame(int GID) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<ArrayList<Player>>() {
			@Override
			public ArrayList<Player> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT team_1, team_2 from games where GID = ?" );
				stmt.setInt( 1, GID);
				resultSet = stmt.executeQuery();

				resultSet.next();

				String team_1 = resultSet.getString("team_1");
				String team_2 = resultSet.getString("team_2");
				String playerCSV = team_1 + "," + team_2;
				String[] players = playerCSV.split(",");

				ArrayList<Player> playersOnTable = new ArrayList<Player>();
				for(String id : players){
					Account account = new Account();
					account.populateAccountData(Integer.parseInt(id));
					playersOnTable.add(new Player(account.getUsername()));
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return playersOnTable;
			}
		});

	}
	/***
	 *
	 * @param value
	 * @param teamID
	 * @param GID
	 * @return
	 * @throws SQLException
	 */
//	public boolean updateGameScore(int value, int teamID, int GID, int uid) throws SQLException {
//		return executeTransaction(new Transaction<Boolean>() {
//			@Override
//			public Boolean execute(Connection conn) throws SQLException {
//				//Connection conn = null;
//				PreparedStatement stmt = null;
//				ResultSet resultSet = null;
//
//				try {
//					if(teamID == 1) {
//						stmt = conn.prepareStatement("update game set score_1 = score_1 + ? where GID = ?");
//					}else{
//						stmt = conn.prepareStatement("update game set score_2 = score_2 + ? where GID = ?");
//					}
//					// substitute the title entered by the user for the placeholder in
//					// the query
//					stmt.setInt(1, value);
//					stmt.setInt(2, GID);
//
//					// execute the query
//					stmt.executeUpdate();
//
//					ArrayList<Integer> gameStats = getGameStats(GID);
//
//					//Check which team the player is on
//					if(teamID == 1){
//						//Player is on team one, find out which player on team one scored
//						String[] t1Players = gameStats.get(0).split(",") ;
//						if(uid == Integer.parseInt(t1Players[0].split("~")[0])){
//							//Parse!
//							//gets the selected player
//							String playerInQuestion = t1Players[0];
//
//							//only get the points associated from the player
//							String[] playersPoints = t1Players[0].split("~");
//
//							//add the value to the player
//							int playerScore = Integer.parseInt(playersPoints[1]);
//							playerScore += value;
//
//							//update the new string for the database
//							String updatedPlayer = uid + "~" + playerScore;
//
//							//sql statement to update
//							stmt = conn.prepareStatement("update game set team_1 = ? where GID = ?");
//							stmt.setString(1, updatedPlayer + "," + t1Players[1]);
//
//						}else{
//							//Parse! see above
//							String playerInQuestion = t1Players[1];
//							String[] playersPoints = t1Players[1].split("~");
//							int playerScore = Integer.parseInt(playersPoints[1]);
//							playerScore += value;
//							String updatedPlayer = uid + "~" + playerScore;
//							stmt = conn.prepareStatement("update game set team_1 = ? where GID = ?");
//							stmt.setString(1, t1Players[0] + "," + updatedPlayer);
//						}
//					}else{
//						if(teamID == 2){
//							//gets players on team 2
//							String[] t2Players = gameStats.get(1).split(",") ;
//							if(uid == Integer.parseInt(t2Players[0].split("~")[0])){
//								String playerInQuestion = t2Players[0];
//								String[] playersPoints = t2Players[0].split("~");
//								int playerScore = Integer.parseInt(playersPoints[1]);
//								playerScore += value;
//								String updatedPlayer = uid + "~" + playerScore;
//								stmt = conn.prepareStatement("update game set team_2 = ? where GID = ?");
//								stmt.setString(1, updatedPlayer + "," + t2Players[1]);
//							}else{
//								String playerInQuestion = t2Players[1];
//								String[] playersPoints = t2Players[1].split("~");
//								int playerScore = Integer.parseInt(playersPoints[1]);
//								playerScore += value;
//								String updatedPlayer = uid + "~" + playerScore;
//								stmt = conn.prepareStatement("update game set team_2 = ? where GID = ?");
//								stmt.setString(1, t2Players[0] + "," + updatedPlayer);
//							}
//						}
//					}
//					stmt.setInt(2, GID);
//					// execute the query
//					stmt.executeUpdate();
//
//
//
//				} finally {
//					DBUtil.closeQuietly(resultSet);
//					DBUtil.closeQuietly(stmt);
//				}
//				return true;
//			}
//		});
//	}


	/***
	 * Gets a userstat list absed on uid
	 * @param gid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getGameStats(int gid) throws SQLException{
		return executeTransaction(new Transaction<ArrayList<Integer> >() {
			@Override
			public ArrayList<Integer> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				ArrayList<Integer> rtnStats = new ArrayList<Integer>();

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT * from game where gid = ?" );// user attribute
				stmt.setInt( 1, gid);
				resultSet = stmt.executeQuery();

				while(resultSet.next()) {
					rtnStats.add(resultSet.getInt("score_1"));
					rtnStats.add(resultSet.getInt("score_2"));
					rtnStats.add(resultSet.getInt("player_1_points"));
					rtnStats.add(resultSet.getInt("player_2_points"));
					rtnStats.add(resultSet.getInt("player_3_points"));
					rtnStats.add(resultSet.getInt("player_4_points"));
					rtnStats.add(resultSet.getInt("player_1_plunks"));
					rtnStats.add(resultSet.getInt("player_2_plunks"));
					rtnStats.add(resultSet.getInt("player_3_plunks"));
					rtnStats.add(resultSet.getInt("player_4_plunks"));
					rtnStats.add(resultSet.getInt("status"));
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return rtnStats;
			}
		});
	}



}
