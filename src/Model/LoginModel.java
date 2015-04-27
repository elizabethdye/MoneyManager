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
	}
	
	public Networker getNetworker() {
		return networker;
	}
	
	public void setNetworker(Networker net){
		networker = net;
	}
}
