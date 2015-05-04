package GUI;

import java.io.IOException;
import java.sql.SQLException;

import Model.DatabaseCommand;
import Model.ErrorMessage;
import Model.LoginModel;
import Model.ServerRequest;
import Model.UserType;
import Networking.Networker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	TextField idField;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	Button login;
	
	String ID;
	String password;
	UserType type;
	LoginModel model;
	Networker networker;
	
	
	@FXML
	private void initialize() throws ClassNotFoundException, SQLException{
		model = new LoginModel();
		networker = model.getNetworker();
		idField.setText("test");
		passwordField.setText("test");
	}
	
	@FXML
	private void login() throws SQLException, IOException{
		UserType user = checkUserType();
		if (user == UserType.USER){
			startMoneyView();
		}
		else if ( user == UserType.ADMIN){
			startAdminView();
		}
		else{
			sendError(); 
		}
	}
	
	private void startAdminView() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminUI.fxml"));
		Parent home_page_parent = (Parent)loader.load();
		
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) login.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
		AdminController controller = (AdminController)loader.getController();
		controller.setUser(ID);
		controller.setModel(model);
	}
	
	private void startMoneyView() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MoneyUI.fxml"));
		Parent home_page_parent = (Parent)loader.load();
		MoneyController controller = (MoneyController)loader.getController();
		controller.setUser(ID);
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) login.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();	
	}
	
	private void sendError(){
		errorWindow();
	}
	
	private UserType checkUserType() throws SQLException{
		ID = idField.getText();
		password = passwordField.getText();
		DatabaseCommand cmd = DatabaseCommand.GET_USER_TYPE;
		String[] args = {ID, password};
		ServerRequest request = new ServerRequest(cmd, args);
		type = (UserType) networker.sendServerRequest(request).getResult();
		return type;
	}
	
	@FXML
	public void errorWindow(){
		ErrorWindow window = new ErrorWindow();
		window.showError(ErrorMessage.USERPASSWORD);
	}

}
