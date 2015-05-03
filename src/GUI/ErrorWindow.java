package GUI;

import Model.ErrorMessage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorWindow {
	public void showError(ErrorMessage message){
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("\n     " + ErrorMessage.returnMessage(message) + "     \n ");
		root.getChildren().addAll(nameField);
		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		newStage.setTitle("ERROR");
	}
}
