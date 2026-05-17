package game.engine.controller;

import game.engine.Game;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.*;
import javafx.scene.input.KeyEvent;

public class SceneController {
    private Scene scene;
    private Stage stage;

    @FXML
    private TextArea GameOverMSG;
    @FXML
    private Button closeButton = new Button();





    

    @FXML
    public void switchToChooseRole(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ChooseRole.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGameInstructions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GameInstructions.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToStartMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGameOver(ActionEvent event, String s) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
        Parent root = loader.load();
        SceneController controller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        TextArea g = controller.getGameOverMSG();
        g.setText(s);
        g.setEditable(false);
    }

    public void switchToGameOver(KeyEvent event, String s) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
        Parent root = loader.load();
        SceneController controller = loader.getController();
        stage = getStage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        TextArea g = controller.getGameOverMSG();
        g.setText(s);
        g.setEditable(false);
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
        controller.initializeCardsUI();
        controller.initializeMonsterImages();
        controller.getJavaConsole();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()) {
                    case W:
                        try {
                            controller.switchToWinScreen(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case E:
                        controller.addEnergy();
                        break;
                    default:
                        break;
                }
            }
        });
        controller.setStage(stage);
    }

   

    public TextArea getGameOverMSG()        { return GameOverMSG; }
    public void setGameOverMSG(TextArea t)  { GameOverMSG = t; }
    public void setStage(Stage s)           { stage = s; }
    public Stage getStage()                 { return stage; }

  

    @FXML
    public void handleCloseButton(ActionEvent event) {
        Window window = closeButton.getScene().getWindow();
        if (window instanceof Stage)
            ((Stage) window).close();
    }

    public void updateGameOverScreen(String s) {
        GameOverMSG = new TextArea();
        GameOverMSG.setText(s);
        GameOverMSG.setEditable(false);
    }
}