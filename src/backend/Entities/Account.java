package backend.Entities;

import backend.Database.AccountQuery;

import java.sql.SQLException;

public class Account {
	private int UID;
	private String username;
	private String email;
	private String name;
	private int type;
	AccountQuery db;

	public Account(){
		db = new AccountQuery();
	}

	public void populateAccountData(int UID){
		try{
			name = db.getAccountName(UID);
			email = db.getAccountEmail(UID);
			username = db.getAccountUserame(UID);
			type = db.getAccountType(UID);

		}catch (SQLException e){
			e.printStackTrace();
		}
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
}
