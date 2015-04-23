package Model;

import java.sql.SQLException;
import Networking.Networker;

public class LoginModel {
	
	Networker networker;
	
	public LoginModel() throws ClassNotFoundException, SQLException{
		networker = new Networker();
		addUser("admin", "admin", UserType.ADMIN);
	}
	
	public void addUser(String ID, String password, UserType type) throws SQLException{
		DatabaseCommand cmd = DatabaseCommand.ADD_USER;
		String[] args = {ID, password, type.toString()};
		ServerRequest request = new ServerRequest (cmd, args);
		networker.sendServerRequest(request);
	}
	
	public Networker getNetworker() {
		return networker;
	}
	
	public void setNetworker(Networker net){
		networker = net;
	}
}
