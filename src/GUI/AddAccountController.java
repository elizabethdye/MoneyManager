package GUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.DatabaseCommand;
import Model.ServerRequest;
import Networking.Networker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAccountController {
	Networker net;
	String ID;
	
    private boolean commited = true;
    private Stage stage;

    @FXML
    private TextField accNameField;
    @FXML
    private TextField balanceField;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public boolean isCommited() {
        return this.commited;
    }
   
    @FXML
    public void handleCommit()throws NumberFormatException{
        String name = accNameField.getText();
        Double balance = Double.valueOf(balanceField.getText());
        
        DatabaseCommand cmd1 = DatabaseCommand.ADD_ACCT;
		String[] args1 = {ID, name};
		ServerRequest request1 = new ServerRequest(cmd1, args1);
		net.sendServerRequest(request1);
		
		DatabaseCommand cmd2 = DatabaseCommand.CREATE_DEPOSIT;
		String[] args2 = {ID, name, Double.toString(balance), getDate()};
		ServerRequest request2 = new ServerRequest(cmd2, args2);
		net.sendServerRequest(request2);

        commited = true;
        stage.close();
    }

    @FXML
    public void handleCancel(){ stage.close();}
    
    public void setUser(String ID){
    	this.ID = ID;
    	net = new Networker();
    }
    
    public String getDate(){
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	Date date = new Date();
 	   	return dateFormat.format(date);
    }
}
