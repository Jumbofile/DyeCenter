package backend;

import java.sql.SQLException;
import java.util.ArrayList;

//import entity.Item;

public interface IDatabase {
	boolean accountExist(String username, String password);
	boolean registerAccount(String userName, String pass, String email, String name) throws SQLException;
	public String getAccountName(String email) throws SQLException;
	public String getGamesPlayed(int UID) throws SQLException;
	public int getAccountID(String email) throws SQLException;
	public ArrayList<Integer> getUserStats(int uid) throws SQLException;
	public boolean modifyStats(String statName, int value, int UID) throws SQLException;


}