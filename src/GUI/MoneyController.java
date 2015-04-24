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
	String userID;
	MoneyModel model;
	Networker net;
	@FXML
	TitledPane Deposit;

	@FXML
	ComboBox toBoxDep;
	@FXML
	DatePicker datePickerDep;
	@FXML
	TextField amountFieldDep;

	@FXML
	ComboBox fromBoxXfer;
	@FXML
	ComboBox toBoxXfer;
	@FXML
	DatePicker datePickerXfer;
	@FXML
	TextField amountFieldXfer;


	@FXML
	public void initialize(){

	}

	@FXML
	public void handleDeposit(){
		String date = datePickerDep.getValue().toString();
		double amount = Double.parseDouble(amountFieldDep.getText());
		String account = toBoxDep.getSelectionModel().getSelectedItem().toString();
		createDeposit(userID, account, amount, date);
	}

	@FXML
	public void handleTransfer(){
		String date = datePickerDep.getValue().toString();
		double amount = Double.parseDouble(amountFieldDep.getText());
		String fromAcc = fromBoxXfer.getSelectionModel().getSelectedItem().toString();
		String toAcc = toBoxXfer.getSelectionModel().getSelectedItem().toString();
		createTransfer(userID, toAcc, fromAcc, amount, date);
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
