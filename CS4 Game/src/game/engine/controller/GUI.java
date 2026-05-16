package game.engine.controller;



import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import game.engine.*;
import game.engine.cells.*;;

public class GUI extends Application{


	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("DoorDash");
		Parent root1 = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
		//Image icon = new Image("/CS4 Game/src/game/engine/controller/Icon.jpg");
		

		Scene s = new Scene(root1);
		primaryStage.setScene(s);
		//primaryStage.getIcons().add(icon);
		primaryStage.show();
		
		
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}
}
