package Networking;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.*;

import Database.Database;
import Model.DatabaseCommand;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Model.UserType;

public class ServerRequestThread extends Thread {
    private Socket socket;
    private Database db;
    
    public ServerRequestThread(Socket socket, Database db) {
        this.socket = socket;
        this.db = db;
    }

    public void run(){
        try {
            ObjectInputStream fromClientStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream toClientStream = new ObjectOutputStream(socket.getOutputStream());
            
            try {
				ServerRequest clientRequest = (ServerRequest) fromClientStream.readObject();
				System.out.println("Got ServerRequest from client; command is " + clientRequest.getCommand().toString());
				ServerRequestResult result = evaluateRequest(clientRequest);
				toClientStream.writeObject(result);
				System.out.println("Sent the ServerRequestResult back to user");
				toClientStream.flush();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            socket.close();
            fromClientStream.close();
            toClientStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private ServerRequestResult evaluateRequest(ServerRequest request) throws SQLException{
    	DatabaseCommand command = request.getCommand();
    	String[] args = request.getArgs();
    	ServerRequestResult result = new ServerRequestResult();
    	
    	switch (command){
    		case CREATE_TABLES:
    			result.setResult(null);
    			break;
    		case ADD_COURSE:
    			db.addCourse(args[0], args[1]);
    			result.setResult(null);
    			break;
    		case ADD_STUDENT:
    			db.addStudent(args[0], args[1], args[2]);
    			result.setResult(null);
    			break;
    		case ADD_GRADE:
    			db.addGrade(args[0], args[1], Double.valueOf(args[2]), args[3], args[4]);
    			result.setResult(null);
    			break;
    	}
    	return result;
    }
}
