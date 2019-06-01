package backend;

import java.sql.SQLException;
import java.util.ArrayList;

//import entity.Item;

public interface IDatabase {
	//boolean registerAccount(String username, String password, String email) throws SQLException;
	
	//int createArea(String name, String para, ArrayList<String> options) throws SQLException;
	boolean accountExist(String username, String password);
	boolean registerAccount(String userName, String pass, String email, String name, String gender, String age, String location) throws SQLException;
	ArrayList<String> getCardData(int area_id) throws SQLException;
	void insertCardData(String namee, String descs, String descl, String authorid, String image, String slack, String typee);
	ArrayList<String> getCardAccountData(String username) throws SQLException;//gets all card data
	int getCardCount()throws SQLException;

		//ArrayList<String> getArea(String id) throws SQLException;
	
	//void insertPlayerLocation(String area);
	
	//String checkAccess(String username);

}