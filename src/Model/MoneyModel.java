package Model;

import Networking.Networker;

public class MoneyModel {
	Networker net;
	String userID;
	String toAcct;
	String fromAcct;
	Double amount;
	String date;
	
	public void setNetworker(Networker net){
		this.net = net;
	}
	
	public void setUser(String name) {
		this.userID = name;
	}
	
	public void setToAcct(String acct) {
		this.toAcct = acct;
	}
	
	public void setFromAcct(String acct) {
		this.fromAcct = acct;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void transfer(){
		
	}
}
