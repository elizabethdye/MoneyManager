package GUI;

import Model.MoneyModel;
import Networking.Networker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MoneyController {

	@FXML
	Label acctAmounts;
	
	@FXML
	Button Deposit;

	@FXML
	ComboBox<String> toBoxDep;
	
	@FXML
	DatePicker depDatePick;
	
	@FXML
	TextField depAmountField;
	
	@FXML
	TextField depCategoryField;

	@FXML
	Button Withdrawal;

	@FXML
	ComboBox<String> withFromBox;
	
	@FXML
	DatePicker withDatePick;
	
	@FXML
	TextField withAmountField;
	
	@FXML
	TextField withCategoryField;
	
	@FXML
	Button Transfer;
	
	@FXML
	ComboBox<String> xferFromBox;
	
	@FXML
	ComboBox<String> xferToBox;
	
	@FXML
	DatePicker xferDatePick;
	
	@FXML
	TextField xferAmountField;
	
	@FXML
	TextField xferCategoryField;
	
	String userID;
	MoneyModel model;
	Networker net;
	ObservableList<String> accts;
	ObservableList<String> depAccts;
	ObservableList<String> withAccts;
	ObservableList<String> xferAcctsTo;
	ObservableList<String> xferAcctsFrom;
	
	

	@FXML
	public void initialize(){
		net = new Networker();
	}

	@FXML
	public void handleDeposit(){
		model.setDate(depDatePick.getValue().toString());
		model.setAmount(Double.parseDouble(depAmountField.getText()));
		model.setToAcct(toBoxDep.getSelectionModel().getSelectedItem().toString());
		model.deposit();
		acctAmounts.setText(model.updateBalances());
	}

	@FXML
	public void handleTransfer(){
		model.setDate(xferDatePick.getValue().toString());
		model.setAmount(Double.parseDouble(xferAmountField.getText()));
		model.setFromAcct(xferFromBox.getSelectionModel().getSelectedItem().toString());
		model.setToAcct(xferToBox.getSelectionModel().getSelectedItem().toString());
		model.setCategory(xferCategoryField.getText());
		model.transfer();
		acctAmounts.setText(model.updateBalances());
	}

	@FXML
	public void handleWithdrawal(){
		model.setDate(withDatePick.getValue().toString());
		model.setAmount(Double.parseDouble(withAmountField.getText()));
		model.setFromAcct(withFromBox.getSelectionModel().getSelectedItem().toString());
		model.setCategory(withCategoryField.getText());
		model.withdrawal();
		acctAmounts.setText(model.updateBalances());
	}
	public void setUser(String name) {
		this.userID = name;
		model = new MoneyModel();
		model.setUser(name);
		model.setNetworker(net);
		accts = model.getAccts();
		depAccts = FXCollections.observableArrayList(accts);
		withAccts = FXCollections.observableArrayList(accts);
		xferAcctsTo = FXCollections.observableArrayList(accts);
		xferAcctsFrom = FXCollections.observableArrayList(accts);
		toBoxDep.setItems(depAccts);
		withFromBox.setItems(withAccts);
		xferFromBox.setItems(xferAcctsFrom);
		xferToBox.setItems(xferAcctsTo);
		acctAmounts.setText(model.updateBalances());
	}
	
	public void setModel(MoneyModel model) {
		this.model = model;
	}

	public void setNetworker(Networker net){
		this.model.setNetworker(net);
	}
}
