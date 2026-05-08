package game.engine.controller;

import java.io.IOException;

import game.engine.*;
import game.engine.cells.Cell;
import game.engine.exceptions.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;



public class Console {
	private Game game;
	private SceneController sc = new SceneController();
	
	public void chooseScarer(ActionEvent e) throws IOException
	{
		game = new Game(Role.SCARER);
		sc.switchToGame(e);
		
		
		
	}
	public void chooseLaugher(ActionEvent e) throws IOException
	{
		game = new Game(Role.LAUGHER);
		sc.switchToGame(e);
		
		
		
	}
	public void rollUI(ActionEvent l) throws InvalidMoveException
	{	try{
		
		game.playTurn();
		
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
