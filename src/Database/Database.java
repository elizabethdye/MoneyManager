package Database;

import java.util.ArrayList;
import java.sql.*;

import Model.UserType;
import Model.TType;

public class Database {
	private Statement stat;
	private String comma = "' , '";
	
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
	
	public void deleteTables() throws SQLException {
		stat.executeUpdate("DROP TABLE Logins");
		stat.executeUpdate("DROP TABLE Transactions");
		stat.executeUpdate("DROP TABLE Balances");
	}
	
	private ArrayList<String> makeQuery(String query) throws ClassNotFoundException, SQLException {
        ResultSet rs = stat.executeQuery(query);
        ArrayList<String> toReturn = new ArrayList<String>();
        while (rs.next()) {
        	toReturn.add(rs.getString(1));
        }
        return toReturn;
	}
	
	public void changePassword(String name, String password) throws SQLException {
		String updateCommand = "UPDATE Logins SET Password = '" + password + "' WHERE UserID = '" + name + "'";
		stat.executeUpdate(updateCommand);
	}
	
	public boolean checkUser(String name) throws SQLException {
		String query = "SELECT * FROM Logins WHERE UserID = '" + name + "'";
		ResultSet rs = stat.executeQuery(query);
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkPassword(String name, String password) throws SQLException {
		String query = "SELECT * FROM Logins WHERE UserID = '" + name + "' AND Password = '" + password + "'";
		ResultSet rs = stat.executeQuery(query);
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
	
	public UserType getUserType(String name, String password) throws ClassNotFoundException, SQLException {
		if (checkPassword(name, password)) {
			return checkType(name, password);
		} else {
			return UserType.INVALID;
		}
	}
	
	private UserType checkType(String name, String password) throws ClassNotFoundException, SQLException {
		String query = "SELECT Type FROM Logins WHERE UserID = '" + name + "' AND Password = '" + password + "'";
		return UserType.fromString(makeQuery(query).get(0));
	}
	
	public boolean addUser(String name, UserType type, String password) throws SQLException {
		if (checkUser(name)) {
			return false;
		} else {
			String insertCommand = "INSERT INTO Logins VALUES('" + name + comma + type.toString() + comma + password + "')";
			stat.executeUpdate(insertCommand);
			return true;
		}
	}
	
	private void createTransaction(String name, String account, TType transactionType, Double amount, String category, String date) throws SQLException, ClassNotFoundException {
		String insertCommand = "INSERT INTO Transactions VALUES('" +
				name + comma +
				account + comma + 
				transactionType.toString() + comma + 
				amount.toString() + comma + 
				category + comma + 
				date + "')";
		stat.executeUpdate(insertCommand);
		Double currentBalance = getBalance(name, account);
		Double newBalance = currentBalance + amount;
		updateBalance(name, account, newBalance);
	}

	public void createTransfer(String name, String accountTo, String accountFrom, Double amount, String date) throws SQLException, ClassNotFoundException {
		createTransaction(name, accountTo, TType.TRANSFERTO, amount, "TransferTo", date);
		createTransaction(name, accountFrom, TType.TRANSFERFROM, -amount, "TransferFrom", date);
	}
	
	public void createDeposit(String name, String account, Double amount, String date) throws SQLException, ClassNotFoundException {
		createTransaction(name, account, TType.DEPOSIT, amount, "Deposit", date);
	}
	
	public void createWithdrawal(String name, String account, Double amount, String category, String date) throws SQLException, ClassNotFoundException {
		createTransaction(name, account, TType.WITHDRAWAL, -amount, category, date);
	}
	
	private void updateBalance(String name, String account, Double amount) throws SQLException {
		String updateCommand = "UPDATE Balances SET Balance = '" + amount.toString() + "' WHERE UserID = '" + name + "' AND Account = '" + account + "'";
		stat.executeUpdate(updateCommand);
	}
	
	public Double getBalance(String name, String account) throws ClassNotFoundException, SQLException {
		String query = "SELECT Balance FROM Balances WHERE UserID = '" + name + "' AND Account = '" + account + "'";
		ArrayList<String> result = makeQuery(query);
		if (result.size() == 0) {
			addAccount(name, account);
			return 0.0;
		} else {
			return Double.parseDouble(result.get(0));
		}
	}
	
	public ArrayList<String> getAccounts(String name) throws ClassNotFoundException, SQLException {
		String query = "SELECT Account FROM Balances WHERE UserID = '" + name + "'";
		return makeQuery(query);
	}
	
	//Check to see if account is created yet
	public void addAccount(String name, String account) throws SQLException {
		String insertCommand = "INSERT INTO Balances VALUES('" + name + comma + account + "' , '0.0')";
		stat.executeUpdate(insertCommand);
	}	

	public ArrayList<String> getCategories(String name, String account) throws ClassNotFoundException, SQLException {
		String query = "SELECT Category FROM Transactions WHERE UserID = '" + name + "' AND Account = '" + account + "'";
		return makeQuery(query);
	}
	
	public ArrayList<Double> getTransactionsForCategory(String name, String account, String category) throws ClassNotFoundException, SQLException {
		String query = "SELECT Amount FROM Transactions WHERE UserID = '" + name + "' AND Account = '" + account + "' AND Category = '" + category + "'";
		ArrayList<String> result = makeQuery(query);
		ArrayList<Double> toReturn = new ArrayList<Double>();
		for (String s: result) {
			toReturn.add(Double.parseDouble(s));
		}
		return toReturn;
	}
	
	public ArrayList<String> getTransactions(String name, String account) throws SQLException {
		String query = "SELECT TransactionType, Amount, Category, Date FROM Transactions WHERE UserID = '" + name + "' AND Account = '" + account + "' AND TransactionType <> '" + TType.DEPOSIT.toString() +"' ORDER BY Date";
        ResultSet rs = stat.executeQuery(query);
        String separator = "~;~";
		ArrayList<String> toReturn = new ArrayList<String>();
        while (rs.next()) {
        	String type = rs.getString("TransactionType");
        	Double amount = rs.getDouble("Amount");
        	String category = rs.getString("Category");
        	String date = rs.getString("Date");
        	String transactionVal = type + separator +
        			amount.toString() + separator +
        			category + separator +
        			date;
        	toReturn.add(transactionVal);
        }
		return toReturn;
	}

}