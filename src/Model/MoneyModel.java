package Model;

import Networking.Networker;

public class MoneyModel {
	Networker net;
	String userID;
	
	public void setNetworker(Networker net){
		this.net = net;
	}
	
	public void setUser(String name) {
		this.userID = name;
	}
	
}
