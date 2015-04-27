package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by My on 4/26/2015.
 */
public class AddAccountController {

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

        //TODO: call method to save the data to the database

        commited = true;
        stage.close();
    }

    @FXML
    public void handleCancel(){ stage.close();}
}
