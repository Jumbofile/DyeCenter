package backend.Database;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameQuery extends DatabaseFactory {
	//date
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public GameQuery(){

	}

	/***
	 *
	 * @param value
	 * @param teamID
	 * @param GID
	 * @return
	 * @throws SQLException
	 */
	public boolean updateGameScore(int value, int teamID, int GID, int uid) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					if(teamID == 1) {
						stmt = conn.prepareStatement("update game set score_1 = score_1 + ? where GID = ?");
					}else{
						stmt = conn.prepareStatement("update game set score_2 = score_2 + ? where GID = ?");
					}
					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setInt(1, value);
					stmt.setInt(2, GID);

					// execute the query
					stmt.executeUpdate();

					ArrayList<String> gameStats = getGameStats(GID);

					//Check which team the player is on
					if(teamID == 1){
						//Player is on team one, find out which player on team one scored
						String[] t1Players = gameStats.get(0).split(",") ;
						if(uid == Integer.parseInt(t1Players[0].split("~")[0])){
							//Parse!
							//gets the selected player
							String playerInQuestion = t1Players[0];

							//only get the points associated from the player
							String[] playersPoints = t1Players[0].split("~");

							//add the value to the player
							int playerScore = Integer.parseInt(playersPoints[1]);
							playerScore += value;

							//update the new string for the database
							String updatedPlayer = uid + "~" + playerScore;

							//sql statement to update
							stmt = conn.prepareStatement("update game set team_1 = ? where GID = ?");
							stmt.setString(1, updatedPlayer + "," + t1Players[1]);

						}else{
							//Parse! see above
							String playerInQuestion = t1Players[1];
							String[] playersPoints = t1Players[1].split("~");
							int playerScore = Integer.parseInt(playersPoints[1]);
							playerScore += value;
							String updatedPlayer = uid + "~" + playerScore;
							stmt = conn.prepareStatement("update game set team_1 = ? where GID = ?");
							stmt.setString(1, t1Players[0] + "," + updatedPlayer);
						}
					}else{
						if(teamID == 2){
							//gets players on team 2
							String[] t2Players = gameStats.get(1).split(",") ;
							if(uid == Integer.parseInt(t2Players[0].split("~")[0])){
								String playerInQuestion = t2Players[0];
								String[] playersPoints = t2Players[0].split("~");
								int playerScore = Integer.parseInt(playersPoints[1]);
								playerScore += value;
								String updatedPlayer = uid + "~" + playerScore;
								stmt = conn.prepareStatement("update game set team_2 = ? where GID = ?");
								stmt.setString(1, updatedPlayer + "," + t2Players[1]);
							}else{
								String playerInQuestion = t2Players[1];
								String[] playersPoints = t2Players[1].split("~");
								int playerScore = Integer.parseInt(playersPoints[1]);
								playerScore += value;
								String updatedPlayer = uid + "~" + playerScore;
								stmt = conn.prepareStatement("update game set team_2 = ? where GID = ?");
								stmt.setString(1, t2Players[0] + "," + updatedPlayer);
							}
						}
					}
					stmt.setInt(2, GID);
					// execute the query
					stmt.executeUpdate();



				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				return true;
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
	public String createGame(int TID, ArrayList<String> teamOne, ArrayList<String> teamTwo) throws SQLException {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					//TODO
					//Create a table with the teams listed, there will need to be a method made that allows the players to be changed
					//This needs to allow users to choose teams on the servlet/jsp side
					Date myDate = new Date();
					String date = sdf.format(myDate);
					//TODO
					String sql = "insert into game(TID, team_1, team_2, score_1, score_2, status, timestamp)values(?, ?, ?, 0, 0, 0, ?)";

					stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					stmt.setInt(1, TID);
					stmt.setString(2, teamOne.get(0) +"," + teamOne.get(1));
					stmt.setString(3, teamTwo.get(0) +"," + teamTwo.get(1));
					stmt.setString(4, date);
					stmt.executeUpdate();

					ResultSet rs = stmt.getGeneratedKeys();
					rs.next();
					int gid = rs.getInt(1);


					//System.out.println("Team 1: "+ teamOne.get(0));
					//System.out.println("Team 2: "+ teamTwo.get(0));

					String playerUIDs = teamOne.get(0).split("~")[0] + "," ;
					playerUIDs += teamOne.get(1).split("~")[0] + "," ;
					playerUIDs += teamTwo.get(0).split("~")[0] + "," ;
					playerUIDs += teamTwo.get(1).split("~")[0] + "," ;

//					players += String.join(",",teamOne) + "," ;
//					players += String.join(",",teamTwo) ;

					addPlayersToTable(TID,playerUIDs) ;

					return Integer.toString(gid);
				} catch (Exception e){
					e.printStackTrace();
					return "-1";
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	/***
	 * Gets a userstat list absed on uid
	 * @param gid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getGameStats(int gid) throws SQLException{
		return executeTransaction(new Transaction<ArrayList<String> >() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				ArrayList<String> rtnStats = new ArrayList<String>();

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT * from game where gid = ?" );// user attribute
				stmt.setInt( 1, gid);
				resultSet = stmt.executeQuery();

				while(resultSet.next()) {
					rtnStats.add(resultSet.getString("team_1"));
					rtnStats.add(resultSet.getString("team_2"));
					rtnStats.add(resultSet.getString("score_1"));
					rtnStats.add(resultSet.getString("score_2"));
					rtnStats.add(resultSet.getString("status"));
					rtnStats.add(resultSet.getString("timestamp"));
					rtnStats.add(resultSet.getString("TID")) ;
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return rtnStats;
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

}
