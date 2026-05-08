package game.engine.controller;


import java.io.IOException;

import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.*;

public class SceneController {
	private Scene scene;
	private Stage stage;
	@FXML
	private Button closeButton =new Button();

	
	public void switchToChooseRole(ActionEvent event) throws IOException 
	{
		 Parent root = FXMLLoader.load(getClass().getResource("ChooseRole.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene= new Scene(root);
		 stage.setScene(scene);
		 stage.show();
	}
	public void switchToGameInstructions(ActionEvent event) throws IOException 
	{
		 Parent root = FXMLLoader.load(getClass().getResource("GameInstructions.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene= new Scene(root);
		 stage.setScene(scene);
		 stage.show();
	}
	
	public void switchToGame(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("gameGUI.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene= new Scene(root);
		 stage.setScene(scene);
		 stage.show();
		
	}
	  
	public void handleCloseButton(ActionEvent event) 
	{
	 
	        Window window = closeButton.getScene().getWindow();
	        if (window instanceof Stage) 
	        {
	            ((Stage) window).close(); 
	        }
	}
}





