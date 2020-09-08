package backend.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

//import gameSqldemo.SQLDemo.RowList;


public class DatabaseFactory implements IDatabase { /// most of the gamePersist package taken from Lab06 ----CITING

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

    public interface Transaction<ResultType> {
        public ResultType execute(Connection conn) throws SQLException;
    }

    private static final int MAX_ATTEMPTS = 10;

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



    //shutdown=true

}