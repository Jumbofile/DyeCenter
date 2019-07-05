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

/////////////////////////////////////////////////////////////////////////
///////////////////// REGISTER ACCOUNT///////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	public boolean registerAccount(String username, String pass, String email, String name) throws SQLException {
        return executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                //Connection conn = null;
                PreparedStatement stmt = null;
                PreparedStatement stmt2 = null;
                ResultSet resultSet = null;

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

						Date myDate = new Date();
						String date = sdf.format(myDate);
						System.out.println(date);

						String sql = "insert into account(username, password, email, name, timestamp)" + " values(?, ?, ?, ?, ?)" ;

                        stmt2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                        stmt2.setString(1, username);
                        stmt2.setString(2, pass);
                        stmt2.setString(3, email);
                        stmt2.setString(4, name);
                        stmt2.setString(5, date);
                        stmt2.executeUpdate();

                        ResultSet rs = stmt2.getGeneratedKeys();
                        rs.next();
                        int uid = rs.getInt(1) ;

						stmt2 = conn.prepareStatement(
								"insert into userstats(UID, points, plunks, wins, loss) values (?,?,?,?,?)"
						);

						stmt2.setInt(1, uid);
						stmt2.setInt(2, 0);
						stmt2.setInt(3, 0);
						stmt2.setInt(4, 0);
						stmt2.setInt(5, 0);
						stmt2.executeUpdate() ;
						
                        return true;

                    } else {
                        return false; // username already exists
                    }

                } finally {
                    DBUtil.closeQuietly(resultSet);
                    DBUtil.closeQuietly(stmt);
                    DBUtil.closeQuietly(stmt2);
                   // DBUtil.closeQuietly(conn);
                }
            }
        });
	}

	/***
	 *
	 * @param statName
	 * @param value
	 * @return True or false
	 * @throws SQLException
	 * @summary Allows you to update the user stats table based on the value and stat name in the DB
	 */
	public boolean modifyStats(String statName, int value) throws SQLException {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

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



				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

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

	public ArrayList<Integer> getUserStats(int uid) throws SQLException{
		return executeTransaction(new Transaction<ArrayList<Integer> >() {
			@Override
			public ArrayList<Integer>  execute(Connection conn) throws SQLException {
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


	public boolean accountExist(String email, String password){ ///checks if account exists
		//Checks if the user exist and if the password matches
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
						"   name varchar(40),"      +
						"	timestamp varchar(100) "+
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
						")"
					);
					stmt.executeUpdate();

					System.out.println("Making game table");
					stmt = conn.prepareStatement( //creates game table
						"create table game ("  +
						"	GID bigint auto_increment," +
						"	players varchar(255)," +
						"	score_1 INT," +
						"	score_2 INT," +
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