package GUI;

import java.io.IOException;
import java.sql.SQLException;

import Model.LoginModel;
import Model.UserTypes;
import Networking.Networker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminController {

	@FXML
	Button	add;
	
	@FXML
	TextField IDField;
	String ID;
	
	@FXML
	PasswordField passwordField;
	String password;
	
	@FXML
	ChoiceBox<String> userType;
	String type;
	
	LoginModel model;
	
	private String userID;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException{
		userType.setItems(FXCollections.observableArrayList("Student","Professor","Admin"));
	}
	
	@FXML
	private void addUser() throws SQLException, IOException{
		ID = IDField.getText();
		password = passwordField.getText();
		type = userType.getSelectionModel().getSelectedItem();
		model.addUser(ID, password, UserTypes.fromString(type));
		showNewStage("LoginUI.fxml");
	}
	
	private void showNewStage(String FXMLFile) throws IOException {
		Parent home_page_parent = FXMLLoader.load(getClass().getResource(FXMLFile));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) add.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}

	public void setUser(String name) {
		this.userID = name;
		
	}
	
	public void setModel(LoginModel model) {
		this.model = model;
	}

	public void setNetworker(Networker net){
		this.model.setNetworker(net);
	}
}
