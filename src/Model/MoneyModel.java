package Model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import Networking.Networker;

public class MoneyModel {
	Networker net;
	String userID;
	String toAcct;
	String fromAcct;
	Double amount;
	String category;
	String date;
	
	
	public ObservableList<String> accts;
	String acctAmounts;
	
	public void setNetworker(Networker net){
		this.net = net;
	}
	
	public void setUser(String name) {
		this.userID = name;
	}
	
	public void setToAcct(String acct) {
		this.toAcct = acct;
		System.out.println("ToAcct: " + this.toAcct);
	}
	
	public void setFromAcct(String acct) {
		this.fromAcct = acct;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void deposit(){
		DatabaseCommand cmd = DatabaseCommand.CREATE_DEPOSIT;
		String[] args = {userID, toAcct, amount.toString(), date};
		ServerRequest request = new ServerRequest(cmd, args);
		net.sendServerRequest(request);
	}
	
	public void withdrawal(){
		DatabaseCommand cmd = DatabaseCommand.CREATE_WITHDRAWAL;
		String[] args = {userID, fromAcct, amount.toString(), date};
		ServerRequest request = new ServerRequest(cmd, args);
		net.sendServerRequest(request);
	}
	
	public void transfer(){
		DatabaseCommand cmd = DatabaseCommand.CREATE_TRANSFER;
		String[] args = {userID, toAcct, fromAcct, amount.toString(), date};
		ServerRequest request = new ServerRequest(cmd, args);
		net.sendServerRequest(request);
	}

	public String updateBalances() {
		getAccts();
		DatabaseCommand cmd = DatabaseCommand.GET_BALANCE;
		acctAmounts = "";
		for( String acct: accts){
			String[] args = {userID, acct};
			ServerRequest request = new ServerRequest(cmd, args);
			Double acctAmount = (Double) net.sendServerRequest(request).getResult();
			acctAmounts += "\t" + acct + ": $" + acctAmount.toString(); 
		}
		
		return acctAmounts;
	}

	public  ObservableList<String> getAccts(){
		DatabaseCommand cmd = DatabaseCommand.GET_ACCTS;
		String[] args = {userID};
		ServerRequest request = new ServerRequest(cmd, args);
		ServerRequestResult result = net.sendServerRequest(request);
		System.out.println("sent request");
		ArrayList<String> acctsTemp = (ArrayList<String>) result.getResult();
		accts = FXCollections.observableArrayList(acctsTemp);
		System.out.println("HERE@getAccts");
		return accts;
	}
	
}
