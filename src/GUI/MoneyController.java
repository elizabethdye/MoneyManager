package GUI;

import Model.ErrorMessage;
import Model.MoneyModel;
import Networking.Networker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class MoneyController {

	@FXML
	Accordion actionContainer;
	@FXML
	TitledPane depPane;

	@FXML
	TextArea acctAmounts;

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

	@FXML
	PieChart pieChart;

	@FXML
	LineChart lineChart;

	@FXML
	Label depMessage;
	@FXML
	Label xferMessage;
	@FXML
	Label withMessage;


	Main main;
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
		actionContainer.setExpandedPane(depPane);
		depDatePick.setValue(LocalDate.now());
		xferDatePick.setValue(LocalDate.now());
		withDatePick.setValue(LocalDate.now());

		depMessage.setText("");
		withMessage.setText("");
		xferMessage.setText("");

		toBoxDep.setValue("Checking");
		withFromBox.setValue("Checking");
		xferFromBox.setValue("Savings");
		xferToBox.setValue("Checking");

	}

	@FXML
	public void handleAccountAdd(){
		String name = "", bal="";
		TextInputDialog nameDialog = new TextInputDialog("Account Name");
		nameDialog.setHeaderText("Account Name");
		nameDialog.setTitle("Account Name");
		nameDialog.setContentText("Please enter the name of the account");
		Optional<String> result = nameDialog.showAndWait();
		if (result.isPresent()){
			name = result.get();

			TextInputDialog balDialog = new TextInputDialog("Current Balance");
			balDialog.setHeaderText("Current Balance");
			balDialog.setTitle("Current Balance");
			balDialog.setContentText("Please enter the current balance the account");
			Optional<String> balance = balDialog.showAndWait();
			if (balance.isPresent()){
				bal = balance.get();
			}else{ 
				return;
			}
		}
		if (!"".equals(name) && !"".equals(bal)){
			if (model != null && !model.checkExists(name)){
				model.addAcct(name);
			}
			else {
				showError(ErrorMessage.ACCT_EXISTS);
			}
			updateAccts();
		}

	}

	@FXML
	public void handleDeposit(){
		if(checkAmount(depAmountField.getText())){
			model.setDate(depDatePick.getValue().toString());
			model.setAmount(Double.parseDouble(depAmountField.getText()));
			model.setToAcct(toBoxDep.getSelectionModel().getSelectedItem().toString());
			model.deposit();
			acctAmounts.setText(model.updateBalances());
			withMessage.setText("");
			depMessage.setText("Deposit accepted.");
			xferMessage.setText("");
			clearDepFields();
			populateCharts();
		}
		else{
			showError(ErrorMessage.AMOUNT);
		}

	}

	@FXML
	public void handleTransfer(){
		if(checkAmount(xferAmountField.getText())){
			model.setDate(xferDatePick.getValue().toString());
			model.setAmount(Double.parseDouble(xferAmountField.getText()));
			model.setFromAcct(xferFromBox.getSelectionModel().getSelectedItem().toString());
			model.setToAcct(xferToBox.getSelectionModel().getSelectedItem().toString());
			if(!model.checkAccts()){
				model.setCategory(xferCategoryField.getText());
				model.transfer();
				acctAmounts.setText(model.updateBalances());
				withMessage.setText("");
				depMessage.setText("");
				xferMessage.setText("Transfer accepted.");
				clearXferFields();
				populateCharts();
			}
			else{
				showError(ErrorMessage.ACCT_SAME);
			}
		}
		else{
			showError(ErrorMessage.AMOUNT);
		}


	}

	@FXML
	public void handleWithdrawal(){
		if(checkAmount(withAmountField.getText())){
			model.setDate(withDatePick.getValue().toString());
			model.setAmount(Double.parseDouble(withAmountField.getText()));
			model.setFromAcct(withFromBox.getSelectionModel().getSelectedItem().toString());
			model.setCategory(withCategoryField.getText());
			model.withdrawal();
			acctAmounts.setText(model.updateBalances());
			withMessage.setText("Withdrawal accepted.");
			depMessage.setText("");
			xferMessage.setText("");
			clearWithFields();
			populateCharts();
		}
		else{
			showError(ErrorMessage.AMOUNT);
		}
	}

	public void clearWithFields(){
		withAmountField.clear();
		withCategoryField.clear();
	}

	public void clearDepFields(){
		depAmountField.clear();

	}

	public void clearXferFields(){
		xferAmountField.clear();
		xferCategoryField.clear();
	}
	public void showError(ErrorMessage errorType){
		ErrorWindow window = new ErrorWindow();
		window.showError(errorType);
	}

	private boolean checkAmount(String amount){


		if(amount.matches("[-+]?[0-9]*\\.?[0-9]+")){
			return true;
		}
		else{
			return false;
		}
	}
	public void setUser(String name) {
		model = new MoneyModel();
		model.setUser(name);
		model.setNetworker(net);
		this.setModel(model);
		this.userID = name;
		updateAccts();
	}

	public void updateAccts() {
		accts = model.getAccts();
		depAccts = FXCollections.observableArrayList(accts);
		withAccts = FXCollections.observableArrayList(accts);
		xferAcctsTo = FXCollections.observableArrayList(accts);
		xferAcctsFrom = FXCollections.observableArrayList(accts);
		toBoxDep.setItems(depAccts);
		withFromBox.setItems(withAccts);
		xferFromBox.setItems(xferAcctsFrom);
		xferToBox.setItems(xferAcctsTo);
		String a = model.updateBalances();
		acctAmounts.setText(model.updateBalances());
		acctAmounts.setId("balance-amount");
		populateCharts();
	}

	public void populateCharts() {
		populatePieChart();
		populateLineChart();
	}

	private void populateLineChart() {
		//		TODO: Awaiting methods from model package

		return;

	}

	private void populatePieChart() {
		//		TODO: Awaiting methods from model package
		ArrayList<String> categories = model.getCategories("Checking");
		Map<String, Double> amountPerCat = new HashMap<>();
		for (String cat : categories) {
			if ( isTranfer(cat) ){ continue;}
			ArrayList<Double> vals = model.getTransactionsForCategory("Checking", cat);
			Double val = sumArrayList(vals);
			amountPerCat.put(cat, val);
		}

		ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
		for (Map.Entry<String,Double> entry : amountPerCat.entrySet()){
			PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
			chartData.add(data);
		}
		pieChart.setData(chartData);
		pieChart.setTitle("Spending based on categories");
	}

	private boolean isTranfer(String cat) {
		return (cat.equals("TransferTo") || cat.equals("TransferFrom"));
	}

	private Double sumArrayList(ArrayList<Double> vals){
		Double res = 0.00;
		for (Double val:vals){
			res += val;
		}
		return res;
	}
	public void setModel(MoneyModel model) {
		this.model = model;
	}
	public void setMain(Main main) {this.main = main;}
	public void setNetworker(Networker net){
		this.model.setNetworker(net);
	}
}
