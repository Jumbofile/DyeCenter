package backend;

import java.sql.SQLException;
import java.util.ArrayList;

//import entity.Item;

public interface IDatabase {
	boolean accountExist(String username, String password);
	boolean registerAccount(String userName, String pass, String email, String name) throws SQLException;
	public String getAccountName(int UID) throws SQLException;
	public String getGamesPlayed(int UID) throws SQLException;
	public int getAccountID(String email) throws SQLException;
	public ArrayList<Integer> getUserStats(int uid) throws SQLException;
	public boolean modifyStats(String statName, int value, int UID) throws SQLException;
	public Object[] createTable(String tableName, int plunk, int uid) throws SQLException;
	public ArrayList<Integer> getGameIDs(int TID) throws SQLException;
	public ArrayList<Integer> getTables(int UID) throws SQLException;
	public String getTableFromGameID(int GID) throws SQLException ;
	public String createGame (int TID, ArrayList<String> teamOne, ArrayList<String> teamTwo) throws SQLException;
	public Integer getPlunk(int TID) throws SQLException;
	public boolean updateUserPoints(int value, int uid) throws SQLException;
	public boolean updateGameScore(int value, int teamID, int GID) throws SQLException;


}