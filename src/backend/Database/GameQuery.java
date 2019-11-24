package backend.Database;

import backend.Entities.Account;
import backend.Entities.Game;
import backend.Entities.Player;
import backend.Entities.Table;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameQuery extends DatabaseFactory {

	public GameQuery(){

	}
	public Table getTableFromGameID(int GID) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<Table>() {
			@Override
			public Table execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				int tid = -1;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT TID from game where GID = ?" );
				stmt.setInt( 1, GID);
				resultSet = stmt.executeQuery();

				resultSet.next();

				tid = resultSet.getInt(1);
				Table table = new Table(tid);
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return table;
			}
		});

	}

	public boolean setGameStatus(int GID, int status) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("UPDATE game set status = ? where GID = ?" );
				stmt.setInt( 1, status);
				stmt.setInt( 2, GID);
				stmt.executeUpdate();

				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return true;
			}
		});

	}

	public Game updateGame(int GID, Game game) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<Game>() {
			@Override
			public Game execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				Game rtnGame = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("UPDATE game SET score_1 = ?, score_2 = ?, player_1_points = ?," +
						"player_2_points = ?, player_3_points = ?, player_4_points = ?, player_1_plunks = ?," +
						"player_2_plunks = ?, player_3_plunks = ?, player_4_plunks = ?, status = ?" +
						"WHERE GID = ?" );
				//team scores
				stmt.setInt( 1, game.getTeam1Score());
				stmt.setInt( 2, game.getTeam2Score());

				//player scores
				stmt.setInt( 3, game.getPlayer1Score());
				stmt.setInt( 4, game.getPlayer2Score());
				stmt.setInt( 5, game.getPlayer3Score());
				stmt.setInt( 6, game.getPlayer4Score());

				//player plunks
				stmt.setInt( 7, game.getPlayer1Plunks());
				stmt.setInt( 8, game.getPlayer2Plunks());
				stmt.setInt( 9, game.getPlayer3Plunks());
				stmt.setInt( 10, game.getPlayer4Plunks());

				//status
				stmt.setInt( 11, game.getStatus());

				//gid
				stmt.setInt( 12, GID);
				stmt.executeUpdate();


				//rtnString = resultSet.getString("name");

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return game;
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
				stmt = conn.prepareStatement("SELECT team_1, team_2 from game where GID = ?" );
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
					if(Integer.parseInt(id) != -1) {
						account.populateAccountData(Integer.parseInt(id));
						playersOnTable.add(new Player(account.getUsername()));
					}else{
						//playersOnTable.add(new Player())
					}
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return playersOnTable;
			}
		});

	}

	public String getTimestamp(int GID) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT timestamp from game where GID = ?" );
				stmt.setInt( 1, GID);
				resultSet = stmt.executeQuery();

				resultSet.next();

				String team_1 = resultSet.getString("timestamp");


				String time = resultSet.getString("timestamp");


				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return time;
			}
		});

	}
	public String getHash(int gid) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT hash from game where GID = ?" );
				stmt.setInt( 1, gid);
				resultSet = stmt.executeQuery();
				String result = new String();

				resultSet.next();
				result = resultSet.getString(1);


				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return result;
			}
		});

	}

	public int getGIDFromHash (String hash) throws SQLException {
		return executeTransaction(new DatabaseFactory.Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT GID from game where hash = ?" );
				stmt.setString( 1, hash);
				resultSet = stmt.executeQuery();
				int result = -1;

				resultSet.next();
				result = resultSet.getInt(1);


				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return result;
			}
		});

	}
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
