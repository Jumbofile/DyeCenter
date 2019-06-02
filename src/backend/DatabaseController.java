package backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

///////////////////////////////////////////////////////////////////////////////////
///////////////////// REGISTER ACCOUNT////////////////////////////////////
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

                        stmt2 = conn.prepareStatement( // enter username
                                "insert into account(username, password, email, name, timestamp)" + "values(?, ?, ?, ?, ?)");

                        stmt2.setString(1, username);
                        stmt2.setString(2, pass);
                        stmt2.setString(3, email);
                        stmt2.setString(4, name);
                        stmt2.setString(5, date);
                        stmt2.execute();

                        Integer uid = getAccountID(username) ;
                        System.out.println(uid);
                        stmt2 = conn.prepareStatement(
							"insert into userstats(points, plunks, wins, loss) values (?,?,?,?)");

						stmt2.setInt(1, uid);
						stmt2.setInt(2, 0);
						stmt2.setInt(3, 0);
						stmt2.setInt(4, 0);
						stmt2.setInt(5, 0);
						stmt2.execute() ;
						
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

	public int getAccountID(String username) throws SQLException{
        return executeTransaction(new Transaction<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                int id = -1;
                //Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet resultSet = null;

                // retreive username attribute from login
                stmt = conn.prepareStatement("SELECT UID from account where username = ?" );// user attribute
				stmt.setString( 1, username);
                resultSet = stmt.executeQuery();

                if (!resultSet.next()) {
                    id = resultSet.getInt(1);
                }

                DBUtil.closeQuietly(resultSet);
                DBUtil.closeQuietly(stmt);
                //DBUtil.closeQuietly(conn);
                return id;
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
    public int getCardCount()throws SQLException{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
					//Connection conn = null;
					PreparedStatement stmt = null;
					ResultSet resultSet = null;

					stmt = conn.prepareStatement(
							"select COUNT(*) "
									+ " from idea"

					);

					resultSet = stmt.executeQuery();
					int returned = -1;
					while (resultSet.next()) {
						returned = Integer.parseInt(resultSet.getString(1));
					}
                    DBUtil.closeQuietly(resultSet);
                    DBUtil.closeQuietly(stmt);
					return returned;
				}
			});
    }
	public ArrayList<String> getCardData(int idea_id) throws SQLException { //gets all card data
		return executeTransaction(new Transaction<ArrayList<String>>() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;


				//Loads from database
				ArrayList<String> content = new ArrayList<String>();

				try {
					stmt = conn.prepareStatement(
							"select * from idea "
									+ "where idea_id = ?"

					);

					//Throws in the area id for sql statement
					stmt.setInt(1, idea_id);

					resultSet = stmt.executeQuery();

					//Turns sql result into an array list then returns it
					while (resultSet.next()) {
						for (int i = 0; i < 8; i++) {
							content.add(resultSet.getString(i + 1));
						}
					}


					return content;

				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					//DBUtil.closeQuietly(conn);
				}
			}
		});
	}
    public void insertCardData(String namee, String descs, String descl, String authorid, String image, String slack, String typee) {
       executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                //Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet resultSet = null;
                System.out.println("Name:" + namee + " \ntype:" + typee + " \ndesc:" + descs + " \ndescl:" + descl + " \nimage:" + image + " \nslack:" + slack + " \nuser:" + authorid);
                try {

                    stmt = conn.prepareStatement(
                            "insert into idea(name, descs, descl, authorid, otherid, image, slack, type)"
                                    + "values(?, ?, ?, ?, ?, ?, ?, ?)"

                    );
                    stmt.setString(1, namee);
                    stmt.setString(2, descs);
                    stmt.setString(3, descl);
                    stmt.setString(4, authorid);
                    stmt.setString(5, "null");
                    stmt.setString(6, image);
                    stmt.setString(7, slack);
                    stmt.setString(8, typee);

                    stmt.execute();
			/*int count = getCardCount();
			stmt = conn.prepareStatement(
					"select idea_id from idea"

			);
			resultSet = stmt.executeQuery();
			int id = -1;
			while(resultSet.next()){
				id = (resultSet.getInt(1));
				if(id == count){
					System.out.println("ID is correct");
				}else{
					stmt = conn.prepareStatement(
							"UPDATE idea" +
								"SET idea_id = ?" +
								"WHERE idea_id = ?;"

					);
					stmt.setInt(1, count);
					stmt.setInt(2, id);
				}
			}*/
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    DBUtil.closeQuietly(stmt);
                   // DBUtil.closeQuietly(conn);
                }
                return true;
            }
        });
    }

    public ArrayList<String> getCardAccountData(String username) throws SQLException { //gets all card data
        return executeTransaction(new Transaction<ArrayList<String>>() {
            @Override
            public ArrayList<String> execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet resultSet = null;


                //Loads from database
                ArrayList<String> content = new ArrayList<String>();
 
                try {
                    stmt = conn.prepareStatement(
                            "select * from account "
                                    + "where username = ?"

                    );

                    //Throws in the area id for sql statement
                    stmt.setString(1, username);

                    resultSet = stmt.executeQuery();

                    //Turns sql result into an array list then returns it
                    while (resultSet.next()) {
                        for (int i = 0; i < 9; i++) {
                            content.add(resultSet.getString(i + 1));
                        }
                    }
                    return content;

                } finally {
                    DBUtil.closeQuietly(resultSet);
                    DBUtil.closeQuietly(stmt);
                   // DBUtil.closeQuietly(conn);
                }
            }
        });
    }


    // The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DatabaseController db = new DatabaseController();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}
}