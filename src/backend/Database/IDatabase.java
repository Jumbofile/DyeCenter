package backend.Database;

import java.sql.SQLException;
import java.util.ArrayList;

//import entity.Item;

public interface IDatabase {
	public Object[] createTable(String tableName, int plunk, int uid) throws SQLException;
	public ArrayList<Integer> getGameIDs(int TID) throws SQLException;
	public ArrayList<Integer> getTables(int UID) throws SQLException;
	public String getTableFromGameID(int GID) throws SQLException ;
	public String createGame (int TID, ArrayList<String> teamOne, ArrayList<String> teamTwo) throws SQLException;
	public boolean updateGameScore(int value, int teamID, int GID, int uid) throws SQLException;
}