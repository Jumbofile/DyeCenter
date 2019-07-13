package backend;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

//import gameSqldemo.SQLDemo.RowList;


public class DatabaseController implements IDatabase { /// most of the gamePersist package taken from Lab06 ----CITING
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load H2 driver");
		}
	}
	
	//decleration
	
	static class RowList extends ArrayList<List<String>> {
		private static final long serialVersionUID = 1L;
	}
	
	private static final String PAD =
			"                                                    " +
			"                                                    " +
			"                                                    " +
			"                                                    ";
		private static final String SEP =
			"----------------------------------------------------" +
			"----------------------------------------------------" +
			"----------------------------------------------------" +
			"----------------------------------------------------";
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	/***
	 * Registers an account when the username and email are unqiue
	 * @param username
	 * @param pass
	 * @param email
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public boolean registerAccount(String username, String pass, String email, String name) throws SQLException {
        return executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                //Connection conn = null;
                PreparedStatement stmt = null;
                PreparedStatement stmt2 = null;
                ResultSet resultSet = null;
                ResultSet resultSet2 = null;

                try {
                    // retreive username attribute from login
                    stmt = conn.prepareStatement("select username " // user attribute
                            + "  from account " // from account table
                            + "  where username = ?"

                    );

                    // substitute the title entered by the user for the placeholder in
                    // the query
                    stmt.setString(1, username);

                    // execute the query
                    resultSet = stmt.executeQuery();

                    if (!resultSet.next()) { /// if username doesnt exist

						// retreive username attribute from login
						stmt2 = conn.prepareStatement("select email " // user attribute
								+ "  from account " // from account table
								+ "  where email = ?"

						);

						stmt2.setString(1, email);

						// execute the query
						resultSet2 = stmt2.executeQuery();

						if (!resultSet2.next()) { /// if email doesnt exist
							Date myDate = new Date();
							String date = sdf.format(myDate);
							System.out.println(date);

							String sql = "insert into account(username, password, email, name, timestamp, account_type)" + " values(?, ?, ?, ?, ?, ?)";

							stmt2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

							stmt2.setString(1, username);
							stmt2.setString(2, pass);
							stmt2.setString(3, email);
							stmt2.setString(4, name);
							stmt2.setString(5, date);
							stmt2.setInt(6, 0);
							stmt2.executeUpdate();

							ResultSet rs = stmt2.getGeneratedKeys();
							rs.next();
							int uid = rs.getInt(1);

							stmt2 = conn.prepareStatement(
									"insert into userstats(UID, points, plunks, wins, loss) values (?,?,?,?,?)"
							);

							stmt2.setInt(1, uid);
							stmt2.setInt(2, 0);
							stmt2.setInt(3, 0);
							stmt2.setInt(4, 0);
							stmt2.setInt(5, 0);
							stmt2.executeUpdate();

							return true;

						} else {
							return false; // email already exists
						}
					}else{
                    	return false;
					}

                } finally {
                    DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
                    DBUtil.closeQuietly(stmt);
                    DBUtil.closeQuietly(stmt2);
                   // DBUtil.closeQuietly(conn);
                }
            }
        });
	}

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
					stmt = conn.prepareStatement("insert into dyetable(name, UID, plunk) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setString(1, tableName);
					stmt.setInt(2, uid);
					stmt.setInt(3, plunk);

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

	/***
	 * Creates a game based on 2 teams and a TID
	 * @param TID
	 * @param teamOne
	 * @param teamTwo
	 * @return
	 * @throws SQLException
	 */
	public boolean createGame(int TID, ArrayList<String> teamOne, ArrayList<String> teamTwo) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					//TODO
					//Create a table with the teams listed, there will need to be a method made that allows the players to be changed
					//This needs to allow users to choose teams on the servlet/jsp side
					Date myDate = new Date();
					String date = sdf.format(myDate);
					//TODO
					//THERE NEEDS TO BE A QUERY THAT WILL RETRIEVE THE PLAYERS UIDs!!!
					stmt = conn.prepareStatement(
							"insert into game(TID, team_1, team_2, score_1, score_2, status, timestamp)" +
								"values(?, ?, ?, 0, 0, 0, ?)");

					// substitute the title entered by the user for the placeholder in
					// the query
					stmt.setInt(1, TID);
					stmt.setString(2, teamOne.get(0) +"," + teamOne.get(1));
					stmt.setString(3, teamTwo.get(0) +"," + teamTwo.get(1));
					stmt.setString(4, date);


					// execute the query
					stmt.execute();

					return true;
				} catch (Exception e){
					e.printStackTrace();
					return false;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}

			}
		});
	}
	/***
	 * Gets account id based on email
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public int getAccountID(String email) throws SQLException{
        return executeTransaction(new Transaction<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                int id = -1;
                //Connection conn = null;
				System.out.println(email);
                PreparedStatement stmt = null;
                ResultSet resultSet = null;

                // retreive username attribute from login
                stmt = conn.prepareStatement("SELECT UID from account where email = ?" );// user attribute
				stmt.setString( 1, email);
                resultSet = stmt.executeQuery();

                if (resultSet.next()) {
                    id = resultSet.getRow();
                }else{
                	System.out.println("Its empty cheif");
				}

                DBUtil.closeQuietly(resultSet);
                DBUtil.closeQuietly(stmt);
                //DBUtil.closeQuietly(conn);
                return id;
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
	 * Returns an array of game ids based on table id
	 * @param TID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getGames(int TID) throws SQLException{
		return executeTransaction(new Transaction<ArrayList<Integer> >() {
			@Override
			public ArrayList<Integer> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				ArrayList<Integer> rtnStats = new ArrayList<Integer>();

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT GID from game where TID = ?" );
				stmt.setInt( 1, TID);
				resultSet = stmt.executeQuery();

				while(resultSet.next()) {
					rtnStats.add(resultSet.getRow());
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

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT name from dyetable where TID = ?" );
				stmt.setInt( 1, TID);
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
	 * Returns an array of table id by uid
	 * @param UID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getTables(int UID) throws SQLException{
		return executeTransaction(new Transaction<ArrayList<Integer> >() {
			@Override
			public ArrayList<Integer> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				ArrayList<Integer> rtnStats = new ArrayList<Integer>();

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT TID from dyetable where UID = ?" );
				stmt.setInt( 1, UID);
				resultSet = stmt.executeQuery();

				while(resultSet.next()) {
					rtnStats.add(resultSet.getRow());
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
	 * gets account name based on email
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public String getAccountName(String email) throws SQLException{
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				String username = null;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select username from account where email = ?"
				);

				stmt.setString(1, email);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					username = resultSet.getString(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return username;
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

	/***
	 * Checks if the account exists based on email
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean accountExist(String email, String password){ // checks if account exists
		// Checks if the user exist and if the password matches
        return executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                //Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet resultSet = null;
                String user = null;
                String pass = null;
                boolean exist = false;
                int count = 0;

                try {

                    // retreive username attribute from login
                    stmt = conn.prepareStatement(
                            "select * from account"
                    );

                    // execute the query
                    resultSet = stmt.executeQuery();

                    //harry = resultSet.getString("username");/// this might not work
                    while (resultSet.next()) {
                        user = resultSet.getString("email");
                        //System.out.println("9" + username + "9");
                        //System.out.println("9" + user + "9");
                        if (email.equals(user)) {

                            pass = resultSet.getString("password");
                            //System.out.println(password);
                            //System.out.println(pass);
                            if (BCrypt.checkpw(password, pass)) {
                                exist = true;
                            }
                        }

                    }

                    //System.out.println(exist);
                    if (exist == true) {
                        return true;//account exists
                    } else {
                        return false;//account doesnt exists
                    }


                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    DBUtil.closeQuietly(resultSet);
                    DBUtil.closeQuietly(stmt);
                    //DBUtil.closeQuietly(conn);
                }
                return false;
            }
        });
	}
	
	//used for printing sql statments
	private static void printRow(List<String> row, List<Integer> colWidths) {
		for (int i = 0; i < row.size(); i++) {
			if (i > 0) {
				System.out.println(" ");
			}
			String item = row.get(i);
			System.out.println(PAD.substring(0, colWidths.get(i) - item.length()));
			System.out.println(item);
		}
		System.out.println("\n");
	}

	private static void printSeparator(List<Integer> colWidths) {
		List<String> sepRow = new ArrayList<String>();
		for (Integer w : colWidths) {
			sepRow.add(SEP.substring(0, w));
		}
		printRow(sepRow, colWidths);
	}

	private static RowList getRows(ResultSet resultSet, int numColumns) throws SQLException {
		RowList rowList = new RowList();
		while (resultSet.next()) {
			List<String> row = new ArrayList<String>();
			for (int i = 1; i <= numColumns; i++) {
				row.add(resultSet.getObject(i).toString());
			}
			rowList.add(row);
		}
		return rowList;
	}

	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	private static List<Integer> getColumnWidths(List<String> colNames, RowList rowList) {
		List<Integer> colWidths = new ArrayList<Integer>();
		for (String colName : colNames) {
			colWidths.add(colName.length());
		}
		for (List<String> row: rowList) {
			for (int i = 0; i < row.size(); i++) {
				colWidths.set(i, Math.max(colWidths.get(i), row.get(i).length()));
			}
		}
		return colWidths;
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:h2:./idea.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}

	/***
	 * Loads init data for testing only
	 */
	public void loadInitialData() { ///taken from lab06
		System.out.println("Loading initial data...");
		try {
			registerAccount("milk", "$2a$10$TXGWMBkf9vSGHzMenI44m.S4a9kcupH8RY64v7QEyHAWxk6u.9uq.", "test@test.test", "zach") ;
		}
		catch (Exception sqle) {
			System.out.println(sqle);
		}
	}

	//shutdown=true
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
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

    // The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DatabaseController db = new DatabaseController();
		db.createTables();

		db.loadInitialData();
		
		System.out.println("Success!");
	}
}