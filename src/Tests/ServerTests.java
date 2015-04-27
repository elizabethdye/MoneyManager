package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.DatabaseCommand;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Model.UserType;
import Networking.Networker;

public class ServerTests {
	Networker net = new Networker();
	ServerTestThread serverThread;
	
	@Before
	public void initServer() throws ClassNotFoundException, IOException, SQLException{
		serverThread = new ServerTestThread();
		serverThread.start();
	}
	
	@After
	public void closeServer() throws ClassNotFoundException, IOException, SQLException{
		serverThread.closeServer();
	}

	@Test
	public void testServer() throws ClassNotFoundException, IOException, SQLException {
		DatabaseCommand cmd = DatabaseCommand.ADD_USER;
		String[] args = {"test", UserType.USER.toString(), "test"};
		ServerRequest req = new ServerRequest(cmd, args);
		net.sendServerRequest(req);
		cmd = DatabaseCommand.GET_USER_TYPE;
		String[] args2 = {"test", "test"};
		req = new ServerRequest(cmd, args2);
		ServerRequestResult res = net.sendServerRequest(req);
		String type = (String) res.getResult();
		assertEquals(UserType.fromString(type), UserType.fromString("User"));
	}
}
