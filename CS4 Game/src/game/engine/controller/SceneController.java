package game.engine.controller;


import game.engine.Game;

import java.io.IOException;

import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.*;

public class SceneController {
	private Scene scene;
	private Stage stage;
	@FXML
	private TextArea GameOverMSG;
	@FXML
	private Button closeButton =new Button();

	@FXML
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
	
	@FXML
	public void switchToGameOver(ActionEvent event) throws IOException
	{
		 Parent root = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene= new Scene(root);
		 stage.setScene(scene);
		
		 stage.show();
		 StringBuilder p = new StringBuilder();
		 p.append("hii");
	}
	
	public void switchToGame(ActionEvent event, Game game) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("gameGUI.fxml"));
	    Parent root = loader.load();
	    Controller controller = loader.getController();
	    controller.setGame(game);
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();

	    controller.updatePlayerUI(game.getPlayer());
	    controller.updateOppUI(game.getOpponent());
	    controller.boardUI();
	}
	@FXML
	public void handleCloseButton(ActionEvent event) 
	{
	 
	        Window window = closeButton.getScene().getWindow();
	        if (window instanceof Stage) 
	        {
	            ((Stage) window).close(); 
	        }
	}
}





