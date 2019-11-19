package backend.Database;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccountQuery extends DatabaseFactory {
	//date
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	/***
	 * Gets account id based on email
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public int getAccountIDFromEmail(String email) throws SQLException{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				int id = -1;
				//Connection conn = null;
				//System.out.println(email);
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT UID from account where email = ?" );// user attribute
				stmt.setString( 1, email);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					id = resultSet.getInt(1);
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

	public int getAccountIDFromUsername(String username) throws SQLException{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				int id = -1;
				//Connection conn = null;
				//System.out.println(email);
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement("SELECT UID from account where username = ?" );// user attribute
				stmt.setString( 1, username);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					id = resultSet.getInt(1);
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

							String sql = "insert into account(username, password, email, name, account_type)" + " values(?, ?, ?, ?, ?)";

							stmt2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

							stmt2.setString(1, username);
							stmt2.setString(2, pass);
							stmt2.setString(3, email);
							stmt2.setString(4, name);
							stmt2.setInt(5, 0);
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
	 * gets account name based on email
	 * @param UID
	 * @return
	 * @throws SQLException
	 */
	public String getAccountName(int UID) throws SQLException{
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				String name = null;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select name from account where UID = ?"
				);

				stmt.setInt(1, UID);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					name = resultSet.getString(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return name;
			}
		});
	}

	/***
	 * gets account name based on email
	 * @param UID
	 * @return
	 * @throws SQLException
	 */
	public String getAccountTimestamp(int UID) throws SQLException{
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				String name = null;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select timestamp from account where UID = ?"
				);

				stmt.setInt(1, UID);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					name = resultSet.getString(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return name;
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

				stmt = conn.prepareStatement("SELECT TID from dyetable where UID = ? ORDER BY TID DESC" );
				stmt.setInt( 1, UID);
				resultSet = stmt.executeQuery();

				while(resultSet.next()) {
					rtnStats.add(resultSet.getRow());
				}

				stmt = conn.prepareStatement("select TID, players from dyetable");

				resultSet = stmt.executeQuery();
				while(resultSet.next()){
					int inter = resultSet.getRow();
					String getIds = resultSet.getString("players");
					String[] playerList = getIds.split(",");
					for(int i = 0; i < playerList.length; i++){
						//System.out.println(playerList[i]);
						if(Integer.parseInt(playerList[i]) == UID){
							if(rtnStats.contains(resultSet.getInt(1))){

							}else {
								rtnStats.add(resultSet.getInt(1));
							}
						}
					}

				}
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				//System.out.println(rtnStats.toString());
				return rtnStats;
			}
		});
	}

	public String getAccountUserame(int UID) throws SQLException{
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				String name = null;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select username from account where UID = ?"
				);

				stmt.setInt(1, UID);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					name = resultSet.getString(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return name;
			}
		});
	}

	public int getAccountType(int UID) throws SQLException{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				int type = -1;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select account_type from account where UID = ?"
				);

				stmt.setInt(1, UID);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					type = resultSet.getInt(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return type;
			}
		});
	}

	public String getAccountEmail(int UID) throws SQLException{
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				String name = null;
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"select email from account where UID = ?"
				);

				stmt.setInt(1, UID);
				resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					name = resultSet.getString(1);
				}

				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return name;
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
}
