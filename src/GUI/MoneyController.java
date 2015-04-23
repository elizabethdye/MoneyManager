package GUI;

import Model.MoneyModel;
import Networking.Networker;
import javafx.fxml.FXML;

public class MoneyController {
	String userID;
	MoneyModel model;
	Networker net;
	
	@FXML
	public void initialize(){
		
	}
	
	public void setUser(String name) {
		this.userID = name;
	}
	
	public void setModel(MoneyModel model) {
		this.model = model;
	}

	public void setNetworker(Networker net){
		this.model.setNetworker(net);
	}
}
