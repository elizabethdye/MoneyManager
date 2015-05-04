package Tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.Database;
import Model.UserType;


public class DatabaseTests { 


	private Database db;
	
	@Before
	public void createDatabase() throws ClassNotFoundException, SQLException {
		db = new Database("jdbc:sqlite:testdb");
	}
	
	@After
	public void deleteDatabase() throws SQLException {
		db.deleteTables();
	}
	
	@Test
	public void testAddingDeposits() throws ClassNotFoundException, SQLException {
		addTwoDeposits();
		assertEquals((Double)125.0, db.getBalance("User1", "Checking"));
		assertEquals((Double)250.0, db.getBalance("User1", "Savings"));
	}
		
	@Test
	public void testCreatingAfterCreated() throws SQLException, ClassNotFoundException {
		addTwoDeposits();
		db.createTables();
		assertEquals((Double)125.0, db.getBalance("User1", "Checking"));
		assertEquals((Double)250.0, db.getBalance("User1", "Savings"));
	}
	
	@Test
	public void testAddingUsers() throws SQLException, ClassNotFoundException {
		db.addUser("User1", UserType.USER, "Password1");
		assertEquals(UserType.USER, db.getUserType("User1", "Password1"));
		assertThat(UserType.ADMIN, not(db.getUserType("User1", "Password1")));
		assertThat(UserType.INVALID, not(db.getUserType("User1", "Password1")));
		db.addUser("Admin1", UserType.ADMIN, "AdminPassword");
		assertEquals(UserType.ADMIN, db.getUserType("Admin1", "AdminPassword"));
		assertThat(UserType.USER, not(db.getUserType("Admin1", "AdminPassword")));
		assertThat(UserType.INVALID, not(db.getUserType("Admin1", "AdminPassword")));
	}
	
	@Test
	public void testFailedLogin() throws SQLException, ClassNotFoundException {
		db.addUser("User1", UserType.USER, "Password1");
		assertEquals(UserType.INVALID, db.getUserType("Prof1", "Wrong Password"));
		assertEquals(UserType.INVALID, db.getUserType("Non-Existant Professor", "Password"));
	}
	
	@Test
	public void testAddingWithdrawals() throws ClassNotFoundException, SQLException {
		addTwoDeposits();
		addTwoWithdrawals();
		assertEquals((Double)50.0, db.getBalance("User1", "Checking"));
		assertEquals((Double)200.0, db.getBalance("User1", "Savings"));
	}
	
	@Test
	public void testGetAccounts() throws ClassNotFoundException, SQLException {
		addTwoDeposits();
		ArrayList<String> results = db.getAccounts("User1");
		assertEquals(2, results.size());
		assertTrue(results.contains("Checking"));
		assertTrue(results.contains("Savings"));
	}
	
	@Test
	public void testTransfer() throws ClassNotFoundException, SQLException {
		addTransfer();
		assertEquals((Double)75.0, db.getBalance("User1", "Checking"));
		assertEquals((Double)300.0, db.getBalance("User1", "Savings"));
	}
	
	@Test
	public void testGetCategories() throws ClassNotFoundException, SQLException {
		addTwoWithdrawals();
		ArrayList<String> checkingCategories = db.getCategories("User1", "Checking");
		ArrayList<String> savingsCategories = db.getCategories("User1", "Savings");
		assertEquals(checkingCategories.size(), 1);
		assertEquals(savingsCategories.size(), 1);
		assertEquals(checkingCategories.get(0), "Groceries");
		assertEquals(savingsCategories.get(0), "Rent");
	}
	
	@Test
	public void testGetCategoriesMultiple() {
		
	}
	
	public void addTwoDeposits() throws SQLException, ClassNotFoundException {
		db.createDeposit("User1", "Checking", 125.0, "March 26");
		db.createDeposit("User1", "Savings", 250.0, "April 27");
	}
	
	public void addTwoWithdrawals() throws ClassNotFoundException, SQLException {
		db.createWithdrawal("User1", "Checking", 75.0, "Groceries", "May 27");
		db.createWithdrawal("User1", "Savings", 50.0, "Rent", "June 27");
	}
	
	public void addTransfer() throws ClassNotFoundException, SQLException {
		addTwoDeposits();
		db.createTransfer("User1", "Savings", "Checking", 50.0, "April 1");
	}
	
	public void addMultipleCategories() throws ClassNotFoundException, SQLException {
		db.createWithdrawal("User1", "Account", 10.0, "Groceries", "May 23");
		db.createWithdrawal("User1", "Account", 10.0, "Rent", "May 23");
		db.createWithdrawal("User1", "Account", 10.0, "Groceries", "May 23");
		db.createWithdrawal("User1", "Account", 10.0, "Movies", "May 23");
		db.createWithdrawal("User1", "Account", 10.0, "Video Games", "May 23");
		db.createWithdrawal("User1", "Account", 10.0, "Travel", "May 23");
		db.createWithdrawal("User1", "Account", 10.0, "Groceries", "May 23");
	}
	
	
//	public ArrayList<String> getCategories(String name, String account) throws ClassNotFoundException, SQLException {
//		String query = "SELECT Category FROM Transactions WHERE UserID = '" + name + "' AND WHERE Account = '" + account + "'";
//		return makeQuery(query);
//	}
//	
//	public ArrayList<Double> getTransactionsForCategory(String name, String account, String category) throws ClassNotFoundException, SQLException {
//		String query = "SELECT Amount FROM Transactions WHERE UserID = '" + name + "' AND WHERE Account = '" + account + "' AND WHERE Category = '" + category + "'";
//		ArrayList<String> result = makeQuery(query);
//		ArrayList<Double> toReturn = new ArrayList<Double>();
//		for (String s: result) {
//			toReturn.add(Double.parseDouble(s));
//		}
//		return toReturn;
//	}
//	
//	public ArrayList<String> getTransactions(String name, String account) throws SQLException {
//		String query = "SELECT TransactionType, Amount, Category, Date FROM Transactions WHERE UserID = '" + name + "' AND WHERE Account = '" + account + "' AND WHERE TransactionType <> '" + TType.DEPOSIT.toString() +"' ORDER BY Date";
//        ResultSet rs = stat.executeQuery(query);
//        String separator = "~;~";
//		ArrayList<String> toReturn = new ArrayList<String>();
//        while (rs.next()) {
//        	String type = rs.getString("TransactionType");
//        	Double amount = rs.getDouble("Amount");
//        	String category = rs.getString("Category");
//        	String date = rs.getString("Date");
//        	String transactionVal = type + separator +
//        			amount.toString() + separator +
//        			category + separator +
//        			date;
//        	toReturn.add(transactionVal);
//        }
//		return toReturn;
//	}
}
