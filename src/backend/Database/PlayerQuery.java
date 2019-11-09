package backend.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerQuery extends DatabaseFactory{
	/***
	 * Updates the statName by value
	 * @param statName
	 * @param value
	 * @return True or false
	 * @throws SQLException
	 * @summary Allows you to update the user stats table based on the value and stat name in the DB
	 */
	public boolean modifyStats(String statName, int value, int uid) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("update userstats set ? = ? + ? where UID = ?");

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setString(1, statName);
					stmt.setString(2, statName);
					stmt.setInt(3, value);
					stmt.setInt(4, uid);


					// execute the query
					resultSet = stmt.executeQuery();


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				return true;
			}
		});
	}

	public boolean setWinners(int[]winTeam, int status, int gid) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;
				//System.out.println("DB val: " + value);
				//System.out.println("DB uid: " + uid);
				for(int i =0; i < 2; i++){
					System.out.println("Team win :" + winTeam[i]);
				}
				try {
					stmt = conn.prepareStatement("update userstats set wins = wins + 1 where UID = ? or ?");

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setInt(1, winTeam[0]);
					stmt.setInt(2, winTeam[1]);


					// execute the query
					stmt.executeUpdate();



					stmt = conn.prepareStatement("update game set status = ? where GID = ?");

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setInt(1, status);
					stmt.setInt(2, gid);


					// execute the query
					stmt.executeUpdate();

					stmt2 = conn.prepareStatement("update userstats set loss = loss + 1 where UID = ? or ?");

					//todo: split method into a make winner and make looser method

					// substitute the title entered by the user for the placeholder in
					// the query
					//stmt2.setInt(1, lossTeam[0]);
					//stmt2.setInt(2, lossTeam[1]);


					// execute the query
					//stmt2.executeUpdate();

				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				return true;
			}
		});
	}
	public boolean setLosers(int[]lossTeam, int status, int gid) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;
				//System.out.println("DB val: " + value);
				//System.out.println("DB uid: " + uid);
				for(int i =0; i < 2; i++){
					System.out.println("Team loss :" + lossTeam[i]);
				}
				try {
					stmt = conn.prepareStatement("update userstats set loss = loss + 1 where UID = ? or ?");

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setInt(1, lossTeam[0]);
					stmt.setInt(2, lossTeam[1]);


					// execute the query
					stmt.executeUpdate();




					//stmt2.executeUpdate();

				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				return true;
			}
		});
	}

	public boolean updateUserPlunks(int uid, int status) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				//System.out.println("DB val: " + value);
				//System.out.println("DB uid: " + uid);
				try {
					if(status == 0) {
						stmt = conn.prepareStatement("update userstats set plunks = plunks + 1 where UID = ?");
					}else{
						stmt = conn.prepareStatement("update userstats set plunks = plunks - 1 where UID = ?");
					}
					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setInt(1, uid);


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
	 * Gets a userstat list absed on uid
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getUserStats(int uid) throws SQLException{
		return executeTransaction(new Transaction<ArrayList<Integer> >() {
			@Override
			public ArrayList<Integer> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				ArrayList<Integer> rtnStats = new ArrayList<Integer>();

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT * from userstats where uid = ?" );// user attribute
				stmt.setInt( 1, uid);
				resultSet = stmt.executeQuery();

				while(resultSet.next()) {
					rtnStats.add(resultSet.getInt("points"));
					rtnStats.add(resultSet.getInt("plunks"));
					rtnStats.add(resultSet.getInt("wins"));
					rtnStats.add(resultSet.getInt("loss"));
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
	 *
	 * @param value
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public boolean updateUserPoints(int value, int uid) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				//System.out.println("DB val: " + value);
				//System.out.println("DB uid: " + uid);
				try {
					stmt = conn.prepareStatement("update userstats set points = points + ? where UID = ?");

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setInt(1, value);
					stmt.setInt(2, uid);


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

	public Integer getPlunk(int TID) throws SQLException{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				int plunk = 0;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select plunk from dyetable where TID = ?"
				);

				stmt.setInt(1, TID);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					plunk = resultSet.getInt(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return plunk;
			}
		});
	}

	/*
    TODO
     */
	public String getGamesPlayed(int UID) throws SQLException{
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				String username = null;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select (wins+loss) as games from userstats where UID = ?"
				);

				stmt.setInt(1, UID);
				resultSet = stmt.executeQuery();

				if (!resultSet.next()) {
					username = resultSet.getString(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return username;
			}
		});
	}

}
