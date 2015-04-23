package Database;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.sql.*;

public class Database {
	private Statement stat;
	
	public Database(String filename) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection(filename);
        stat = con.createStatement();
        createTables();
	}
	
	public void createTables() {
		try {
			stat.executeUpdate("CREATE TABLE Logins (UserID TEXT, Type TEXT, Password TEXT)");
			stat.executeUpdate("CREATE TABLE Transactions (UserID TEXT, Account TEXT, TransactionType TEXT, Amount REAL, Category TEXT, Date STRING)");
			stat.executeUpdate("CREATE TABLE Balances (UserID TEXT, Account TEXT, Balance REAL)");
		}
		catch (SQLException sq){
			return;
		}
	}
			
	private ArrayList<String> makeQuery(String query) throws ClassNotFoundException, SQLException {
        ResultSet rs = stat.executeQuery(query);
        ArrayList<String> toReturn = new ArrayList<String>();
        while (rs.next()) {
        	toReturn.add(rs.getString(1));
        }
        return toReturn;
	}
	
	private ArrayList<String> getColInfo(String database, String column) throws ClassNotFoundException, SQLException {
        return makeQuery("SELECT " + column + " FROM " + database + " GROUP BY " + column);
	}
			
	public void updateColumn(String database, String name, String column, String update) throws ClassNotFoundException, SQLException {
		String updateCommand = "UPDATE " + database + " SET " + column + " = '" + update + "' WHERE Name = '" + name + "'";
		stat.executeUpdate(updateCommand);
	}
	
	public void changePassword(String name, String password) throws SQLException {
		String updateCommand = "UPDATE Logins SET Password = '" + password + "' WHERE Name = '" + name + "'";
		stat.executeUpdate(updateCommand);
	}
	
	public boolean checkPassword(String name, String password) throws SQLException {
		String query = "SELECT * FROM Logins WHERE Name = '" + name + "' AND Password = '" + password + "'";
		ResultSet rs = stat.executeQuery(query);
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
}
