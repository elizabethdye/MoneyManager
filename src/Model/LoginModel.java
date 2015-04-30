package Model;

import java.sql.SQLException;

import Networking.Networker;

public class LoginModel {
	
	Networker networker;
	
	public LoginModel() throws ClassNotFoundException, SQLException{
		networker = new Networker();
		addUser("admin", UserType.ADMIN, "admin");
	}
	
	public void addUser(String ID, UserType type, String password) throws SQLException{
		DatabaseCommand cmd = DatabaseCommand.ADD_USER;
		String[] args = {ID, type.toString(), password};
		ServerRequest request = new ServerRequest (cmd, args);
		networker.sendServerRequest(request);
		addAccts(ID);
	}
	
	private void addAccts(String ID){
		DatabaseCommand cmd1 = DatabaseCommand.ADD_ACCT;
		String[] args1 = {ID, "Checking"};
		ServerRequest request1 = new ServerRequest(cmd1, args1);
		networker.sendServerRequest(request1);
		
		DatabaseCommand cmd2 = DatabaseCommand.ADD_ACCT;
		String[] args2 = {ID, "Saving"};
		ServerRequest request2 = new ServerRequest(cmd2, args2);
		networker.sendServerRequest(request2);
		
		DatabaseCommand cmd3 = DatabaseCommand.CREATE_DEPOSIT;
		String[] args3 = {ID, "Checking", "1.0", "20150415"};
		ServerRequest request3 = new ServerRequest(cmd3, args3);
		networker.sendServerRequest(request3);
		
		DatabaseCommand cmd4 = DatabaseCommand.CREATE_DEPOSIT;
		String[] args4 = {ID, "Savings", "1.0", "20150415"};
		ServerRequest request4 = new ServerRequest(cmd4, args4);
		networker.sendServerRequest(request4);
	}
	public Networker getNetworker() {
		return networker;
	}
	
	public void setNetworker(Networker net){
		networker = net;
	}
}
