package backend.Entities;

import backend.Database.AccountQuery;

import java.sql.SQLException;
import java.util.ArrayList;

public class Account {
	private int UID;
	private String username;
	private String email;
	private String name;
	private String timeOfCreation;
	private int type;
	private AccountQuery db;
	private ArrayList<Integer> tableIds;

	public Account(){
		db = new AccountQuery();
	}

	public boolean registerAccount(String username, String pass, String email, String name){
		boolean success = false;
		try {
			success = db.registerAccount(username, pass, email, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean accountExists(String email, String password){
		return db.accountExist(email, password);
	}

	public int getAccountID(String email){
		int accountID = -1;
		try {
			accountID = db.getAccountIDFromEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountID;
	}

	public void populateAccountData(int UID){
		try{
			name = db.getAccountName(UID);
			email = db.getAccountEmail(UID);
			username = db.getAccountUserame(UID);
			type = db.getAccountType(UID);
			timeOfCreation = db.getAccountTimestamp(UID);
			tableIds = db.getTables(UID);

		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public Player getPlayerFromAccount(){
		return new Player(username);
	}
	public int getUID(String username){
		int uid = -1;
		try {
			uid =  db.getAccountIDFromUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uid;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getTimeOfCreation() {
		return timeOfCreation;
	}

	public String getUsername() {
		return username;
	}

	public ArrayList<Integer> getTableIds() {
		return tableIds;
	}
}
