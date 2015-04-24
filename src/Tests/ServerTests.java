package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.DatabaseCommand;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Networking.Networker;
import Networking.Server;

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
	public void testAddCourse() throws ClassNotFoundException, IOException, SQLException {
		DatabaseCommand cmd = DatabaseCommand.ADD_COURSE;
		String[] args = {"Course1", "Prof1"};
		ServerRequest req = new ServerRequest(cmd, args);
		net.sendServerRequest(req);
		cmd = DatabaseCommand.GET_COURSES;
		String[] args2 = {"Prof1"};
		req = new ServerRequest(cmd, args2);
		ServerRequestResult res = net.sendServerRequest(req);
		ArrayList<String> courses = (ArrayList<String>) res.getResult();
		assertArrayEquals(courses.toArray(), new String[]{"Course1"});
	}
}
