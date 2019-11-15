package backend.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseBuilder extends DatabaseFactory {
	public void createTables() {
		executeTransaction(new DatabaseFactory.Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					System.out.println("Making account table");

					stmt = conn.prepareStatement( //creates account table
							"create table account (" +
									"	UID bigint auto_increment," +
									"	username varchar(40),"  +
									"	password varchar(100)," +
									"   email varchar(40),"     +
									"   name varchar(200),"      +
									"	timestamp varchar(100), " +
									"   account_type INT" +
									")"
					);
					stmt.executeUpdate();


					System.out.println("Making userstats table");
					stmt = conn.prepareStatement( //creates userstats table
							"create table userstats ("  +
									"	UID INT,	"+
									"	points INT," +
									"	plunks INT," +
									"   wins INT,"   +
									"	loss INT"	 +
									")"
					);
					stmt.executeUpdate();

					System.out.println("Making dyetable table");
					stmt = conn.prepareStatement( //creates dyetable table
							"create table dyetable ("  +
									"	TID bigint auto_increment," +
									"	name varchar(50)," +
									"	UID INT,"+
									"	plunk INT,"+
									"   players varchar(255), " +
									")"
					);
					stmt.executeUpdate();

					System.out.println("Making game table");
					stmt = conn.prepareStatement( //creates game table
							"create table game ("  +
									"	GID bigint auto_increment," +
									"   TID INT, " +
									"	team_1 varchar(20)," +
									"	team_2 varchar(20)," +
									"	score_1 INT," +
									"	score_2 INT," +
									"	player_1_points INT," +
									"	player_2_points INT," +
									"	player_3_points INT," +
									"	player_4_points INT," +
									"	player_1_plunks INT," +
									"	player_2_plunks INT," +
									"	player_3_plunks INT," +
									"	player_4_plunks INT," +
									"	status INT," +
									"	timestamp varchar(100) "	+
									")"
					);
					stmt.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	/***
	 * Loads init data for testing only
	 */
	public void loadInitialData() { ///taken from lab06
		System.out.println("Loading initial data...");
		try {
			AccountQuery aq = new AccountQuery();
			aq.registerAccount("milk", "$2a$10$TXGWMBkf9vSGHzMenI44m.S4a9kcupH8RY64v7QEyHAWxk6u.9uq.", "test@test.test", "zach") ;
			aq.registerAccount("greg", "$2a$10$TXGWMBkf9vSGHzMenI44m.S4a9kcupH8RY64v7QEyHAWxk6u.9uq.", "test1@test.test", "greg") ;
			aq.registerAccount("zach", "$2a$10$TXGWMBkf9vSGHzMenI44m.S4a9kcupH8RY64v7QEyHAWxk6u.9uq.", "test2@test.test", "zach") ;
			aq.registerAccount("tyler", "$2a$10$TXGWMBkf9vSGHzMenI44m.S4a9kcupH8RY64v7QEyHAWxk6u.9uq.", "test3@test.test", "tyler") ;
		}
		catch (Exception sqle) {
			System.out.println(sqle);
		}
	}

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DatabaseBuilder db = new DatabaseBuilder();
		db.createTables();

		db.loadInitialData();

		System.out.println("Success!");
	}
}
