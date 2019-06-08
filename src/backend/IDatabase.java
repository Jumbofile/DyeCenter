package backend;

import java.sql.SQLException;
import java.util.ArrayList;

//import entity.Item;

public interface IDatabase {
	//boolean registerAccount(String username, String password, String email) throws SQLException;
	
	//int createArea(String name, String para, ArrayList<String> options) throws SQLException;
	boolean accountExist(String username, String password);
	boolean registerAccount(String userName, String pass, String email, String name) throws SQLException;
	public String getAccountName(String email) throws SQLException;


}