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
	public void testGetCategoriesMultiple() throws ClassNotFoundException, SQLException {
		addMultipleCategories();
		ArrayList<String> categories = db.getCategories("User1", "Account");
		assertEquals(categories.size(), 5);
		assertEquals(categories.get(0), "Groceries");
		assertEquals(categories.get(1), "Movies");
		assertEquals(categories.get(2), "Rent");
		assertEquals(categories.get(3), "Travel");
		assertEquals(categories.get(4), "Video Games");
	}
	
	@Test
	public void testGetTransactionsForCategory() throws ClassNotFoundException, SQLException {
		addMultipleCategories();
		ArrayList<Double> groceriesAmounts = db.getTransactionsForCategory("User1", "Account", "Groceries");
		ArrayList<Double> travelAmounts = db.getTransactionsForCategory("User1", "Account", "Travel");
		ArrayList<Double> movieAmounts = db.getTransactionsForCategory("User1", "Account", "Movies");
		assertEquals(groceriesAmounts.size(), 2);
		assertTrue(groceriesAmounts.contains(-10.0));
		assertTrue(groceriesAmounts.contains(-15.0));
		assertFalse(groceriesAmounts.contains(-25.0));
		assertEquals(travelAmounts.size(), 2);
		assertTrue(travelAmounts.contains(-70.0));
		assertTrue(travelAmounts.contains(-40.0));
		assertFalse(travelAmounts.contains(-25.0));
		assertEquals(movieAmounts.size(), 1);
		assertTrue(movieAmounts.contains(-20.0));
		assertFalse(movieAmounts.contains(-25.0));
	}
	
	@Test
	public void testGetTransactions() throws ClassNotFoundException, SQLException {
		addMultipleCategories();
		ArrayList<String> transactions = db.getTransactions("User1", "Account");
		assertEquals(transactions.size(), 7);
		assertTrue(transactions.contains("WITHDRAWAL~;~-20.0~;~Movies~;~April 17"));
		assertTrue(transactions.contains("WITHDRAWAL~;~-15.0~;~Groceries~;~April 23"));
		assertTrue(transactions.contains("WITHDRAWAL~;~-40.0~;~Travel~;~May 1"));
		assertTrue(transactions.contains("WITHDRAWAL~;~-30.0~;~Rent~;~May 13"));
		assertTrue(transactions.contains("WITHDRAWAL~;~-10.0~;~Groceries~;~May 27"));
		assertTrue(transactions.contains("WITHDRAWAL~;~-70.0~;~Travel~;~May 30"));
		assertTrue(transactions.contains("WITHDRAWAL~;~-50.0~;~Video Games~;~June 23"));
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
		db.createWithdrawal("User1", "Account", 10.0, "Groceries", "May 27");
		db.createWithdrawal("User1", "Account", 30.0, "Rent", "May 13");
		db.createWithdrawal("User1", "Account", 15.0, "Groceries", "April 23");
		db.createWithdrawal("User1", "Account", 20.0, "Movies", "April 17");
		db.createWithdrawal("User1", "Account", 50.0, "Video Games", "June 23");
		db.createWithdrawal("User1", "Account", 70.0, "Travel", "May 30");
		db.createWithdrawal("User1", "Account", 40.0, "Travel", "May 1");
	}

}
