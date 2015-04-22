package Networking;

import java.net.*;
import java.sql.SQLException;
import java.io.*;

import Database.Database;

public class Server {
	private ServerSocket accepter;
	private Database db;
	private String filename = "jdbc:sqlite:db";
	
	public Server(int port) throws IOException, ClassNotFoundException, SQLException{
		db = new Database(filename);
		accepter = new ServerSocket(port);
		System.out.println("Server IP address: " + accepter.getInetAddress());
	}
	
	public Server(int port, String dbName) throws ClassNotFoundException, SQLException, IOException{
		db = new Database(dbName);
		accepter = new ServerSocket(port);
		System.out.println("Server IP address: " + accepter.getInetAddress());
	}

	public void listen() throws IOException {
		for (;;) {
			Socket s = accepter.accept();
			ServerRequestThread requestThread = new ServerRequestThread(s, db);
			System.out.println("Connection accepted from " + s.getInetAddress());
			requestThread.start();
		}
	}
	
	public void clearDatabase() throws SQLException{
		if (db != null){
			db.deleteTables();
			db.createTables();
		}
	}
	
	public void close() throws IOException{
		accepter.close();
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		Server s = new Server(8888);
		s.listen();
	}
}