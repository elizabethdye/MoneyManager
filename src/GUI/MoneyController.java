package GUI;

import Model.MoneyModel;
import Networking.Networker;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import java.time.LocalDate;
import java.util.Date;

public class MoneyController {

	
	@FXML
	TitledPane Deposit;

	@FXML
	ComboBox toBoxDep;
	
	@FXML
	DatePicker depDatePick;
	
	@FXML
	TextField depAmountField;

	@FXML
	ComboBox xferFromBox;
	
	@FXML
	ComboBox xferToBox;
	
	@FXML
	DatePicker xferDatePick;
	
	@FXML
	TextField xferAmountField;
	
	String userID;
	MoneyModel model;
	Networker net;
	

	@FXML
	public void initialize(){

	}

	@FXML
	public void handleDeposit(){
		model.setDate(depDatePick.getValue().toString());
		model.setAmount(Double.parseDouble(depAmountField.getText()));
		model.setToAcct(toBoxDep.getSelectionModel().getSelectedItem().toString());
		model.deposit();
	}

	@FXML
	public void handleTransfer(){
		model.date = depDatePick.getValue().toString();
		model.amount = Double.parseDouble(depAmountField.getText());
		model.fromAcct = xferFromBox.getSelectionModel().getSelectedItem().toString();
		model.toAcct = xferToBox.getSelectionModel().getSelectedItem().toString();
		model.transfer();
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
