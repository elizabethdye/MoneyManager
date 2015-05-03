package GUI;

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

public class MoneyController {

	@FXML
	Accordion actionContainer;
	@FXML
	TitledPane depPane;

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

	@FXML
	PieChart pieChart;

	@FXML
	LineChart lineChart;

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
			if (model!= null){model.addAcct(name);}
			else {
				System.out.println("it's fucking null");
			}
			updateAccts();
		}

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
		populateChart();
	}

	public void populateChart() {
		populatePieChart();
		populateLineChart();
	}

	private void populateLineChart() {
//		TODO: Awaiting methods from model package

		return;

	}

	private void populatePieChart() {
//		TODO: Awaiting methods from model package
//		ArrayList<String> categories = model.getCatergories();
//		Map<String, Double> amountPerCat = new HashMap<>();
//		for (String cat : categories){
//			ArrayList<Double> vals = model.getTransactionsForCategory(cat);
//			Double val = sumArrayList(vals);
//			amountPerCat.put(cat, val);
//		}
		System.out.println("Tesing chart data");
		Map<String, Double> amountPerCat = new HashMap<>();
		amountPerCat.put("Apple",10.0);
		amountPerCat.put("Banana",20.0);
		amountPerCat.put("Cherry",40.0);
		amountPerCat.put("Duriant",50.5);
		amountPerCat.put("Energy",10.0);


		ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
		for (Map.Entry<String,Double> entry : amountPerCat.entrySet()){
			PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
			chartData.add(data);
		}
		pieChart.setData(chartData);
		pieChart.setTitle("Spending based on categories");



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
