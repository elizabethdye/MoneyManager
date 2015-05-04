package Networking;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.*;

import Database.Database;
import Model.DatabaseCommand;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Model.TType;
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
    
    private ServerRequestResult evaluateRequest(ServerRequest request) throws SQLException, NumberFormatException, ClassNotFoundException{
    	DatabaseCommand command = request.getCommand();
    	String[] args = request.getArgs();
    	ServerRequestResult result = new ServerRequestResult();
    	
    	switch (command){
    		case CREATE_TABLES:
    			result.setResult(null);
    			break;
    		case ADD_USER:
    			result.setResult(db.addUser(args[0], UserType.valueOf(args[1]), args[2]));
    			break;
    		case GET_USER_TYPE:
    			result.setResult(db.getUserType(args[0], args[1]));
    			break;
    		case CREATE_TRANSFER:
    			db.createTransfer(args[0], args[1], args[2], Double.valueOf(args[3]), args[4]);
    			result.setResult(null);
    			break;
    		case CREATE_DEPOSIT:
    			db.createDeposit(args[0], args[1], Double.valueOf(args[2]), args[3]);
    			result.setResult(null);
    			break;
    		case CREATE_WITHDRAWAL:
    			db.createWithdrawal(args[0], args[1], Double.valueOf(args[2]), args[3], args[4]);
    			result.setResult(null);
    			break;
    		case GET_BALANCE:
    			result.setResult(db.getBalance(args[0], args[1]));
    			break;
    		case GET_ACCTS:
    			result.setResult(db.getAccounts(args[0]));
    			break;
    		case ADD_ACCT:
    			db.addAccount(args[0], args[1]);
    			result.setResult(null);
    			break;
    		case GET_CATEGORIES:
    			result.setResult(db.getCategories(args[0], args[1]));
    			break;
    		case GET_TRANSACTIONS:
    			result.setResult(db.getTransactions(args[0], args[1]));
    			break;
    		case GET_T_FOR_CAT:
    			result.setResult(db.getTransactionsForCategory(args[0], args[1], args[2]));
    			break;
    	}
    	return result;
    }
}
