package Tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;

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
	
	public void addTwoDeposits() throws SQLException, ClassNotFoundException {
		db.createDeposit("User1", "Checking", 125.0, "March 26");
		db.createDeposit("User1", "Savings", 250.0, "April 27");
	}
	
	public void addTwoWithdrawals() throws ClassNotFoundException, SQLException {
		db.createWithdrawal("User1", "Checking", 75.0, "Groceries", "May 27");
		db.createWithdrawal("User1", "Savings", 50.0, "Rent", "June 27");
	}
}
