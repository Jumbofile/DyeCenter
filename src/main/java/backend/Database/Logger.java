package backend.Database;

import backend.Entities.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Logger extends DatabaseFactory{


	public boolean addLog(String username, int uid, String servlet, String method, String dbAction, String object, int objectId, String event, String desc) throws SQLException{
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// retreive username attribute from login
				stmt = conn.prepareStatement(
						"INSERT INTO dyelog (username, uid, servlet, method, dbaction, object, objectID, event, desc)"+
								"VALUES (?,?,?,?,?,?,?,?,?)"
				);
				stmt.setString(1, username);
				stmt.setInt(2, uid);
				stmt.setString(3, servlet);
				stmt.setString(4, method);
				stmt.setString(5, dbAction);
				stmt.setString(6, object);
				stmt.setInt(7, objectId);
				stmt.setString(8, event);
				stmt.setString(9, desc);

				stmt.executeUpdate();



				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				//DBUtil.closeQuietly(conn);
				return true;
			}
		});
	}

}
