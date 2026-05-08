package game.engine.controller;

import java.io.IOException;

import game.engine.*;
import game.engine.cells.Cell;
import game.engine.exceptions.*;
import game.engine.monsters.Monster;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;



public class Console {
	@FXML
	private Game game;
	private SceneController sc = new SceneController();
	@FXML
	public TextArea playerTxt = new TextArea("");
	@FXML
	private ProgressBar playerEnergy;
	@FXML
	private Label oppTxt = new Label("");
	
	public void chooseScarer(ActionEvent e) throws IOException	
	{
		game = new Game(Role.SCARER);
		sc.switchToGame(e);
		System.out.println("Your Monster is: "+game.getPlayer().getName());
		updatePlayerUI(game.getPlayer());
		updateOppUI(game.getOpponent());
		
		
	}
	public void chooseLaugher(ActionEvent e) throws IOException
	{
		game = new Game(Role.LAUGHER);
		sc.switchToGame(e);
		updatePlayerUI(game.getPlayer());
		updateOppUI(game.getOpponent());
		System.out.println("Your Monster is: "+game.getPlayer().getName());
		
	}
	public void rollUI(ActionEvent l) throws InvalidMoveException
	{	try{
		
		game.playTurn();
		updatePlayerUI(game.getPlayer());
		updateOppUI(game.getOpponent());
		}
	catch(Exception e){
		e.printStackTrace();
		game.playTurn();}
	}
	public void usePowerUpUI(ActionEvent l) throws OutOfEnergyException
	{
		try
		{game.usePowerup();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
	
	public void updatePlayerUI(Monster player)
	{
		String s = "Player: " + player.getName()+"\nEnergy: "+ player.getEnergy()+ "\nOriginal Role: " + player.getOriginalRole();
		if(player.isConfused())
				s+= "\nCurrent Role: " + player.getRole()+ "\nConfusion turns left: "+ player.getConfusionTurns();		
		if(player.isFrozen())
			s+="\nFrozen";
		if(player.isShielded())
			s+="\nShield";
		playerTxt.appendText(s);
	}
	public void updateOppUI(Monster opp)
	{
		String s = "Opponent: " + opp.getName()+"\nEnergy: "+ opp.getEnergy()+ "\nOriginal Role: " + opp.getOriginalRole();
		if(opp.isConfused())
				s+= "\nCurrent Role: " + opp.getRole()+ "\nConfusion turns left: "+ opp.getConfusionTurns();
		if(opp.isFrozen())
			s+="\nFrozen";
		if(opp.isShielded())
			s+="\nShield";
		oppTxt.setText(s);
	}
//	public void setCellImages(GridPane gp)
//	{
//		
//		
//		for (int i = 0; i < Constants.BOARD_ROWS; i++) {
//			for (int j = 0; j <  Constants.BOARD_COLS; j++) {
//				Cell c= game.getBoard().getBoardCells()[i][j];
////				if(c instanceof Cell)
//					
//			}
//			
//		}
//		
//	}
}
