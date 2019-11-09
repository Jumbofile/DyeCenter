package backend.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TableQuery extends DatabaseFactory{

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
	 * Creates a table
	 * @param tableName
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
