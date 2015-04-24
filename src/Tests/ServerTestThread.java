package Tests;

import java.io.IOException;
import java.sql.SQLException;

import Networking.Server;

public class ServerTestThread extends Thread {
	Server server;
	
	public ServerTestThread() throws ClassNotFoundException, IOException, SQLException {
		server = new Server(8888, "jdbc:sqlite:servertestdb");
		server.clearDatabase();
	}
	
	public void closeServer() throws IOException{
		server.close();
	}
	
	public void run(){
		try {
			server.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
