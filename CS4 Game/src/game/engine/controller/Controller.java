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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;



public class Controller {
	@FXML
	private Game game;
	private SceneController sc = new SceneController();
	@FXML
	public TextArea playerTxt = new TextArea("");
	@FXML
	private ProgressBar playerEnergy;
	@FXML
	private TextArea oppTxt = new TextArea("");
	@FXML
	private GridPane boardGrid;
	private StackPane[] cells;
	
	
	
	private void boardUI() {
		cells = new StackPane[100];
		for (int i = 0; i < cells.length; i++) {
			StackPane cell = createCell(i);
			cells[i] = cell;
			boardGrid.add(cell, indexToRowCol(i)[1], indexToRowCol(i)[0] );
		}
		
	}
	private int[] indexToRowCol(int index) {
	    int cols = Constants.BOARD_COLS;
	    int row = index / cols;
	    int col = index % cols;
	    if (row % 2 == 1)
	        col = cols - 1 - col;
	    row = (Constants.BOARD_ROWS - 1) - row; 
	    return new int[]{row, col};
	}
	private StackPane createCell(int i) {
		Rectangle r = new Rectangle(60, 60, Color.WHITE);
		Label l = new Label(i+ " ");
		StackPane sp = new StackPane();
		sp.getChildren().addAll(r,l);
		return sp;
	}
	
	
	
	
	
	public void chooseScarer(ActionEvent e) throws IOException	
	{
		game = new Game(Role.SCARER);
		sc.switchToGame(e);
		System.out.println("Your Monster is: "+game.getPlayer().getName());
		updatePlayerUI(game.getPlayer());
		updateOppUI(game.getOpponent());
		boardUI();
		
	}
	public void chooseLaugher(ActionEvent e) throws IOException
	{
		game = new Game(Role.LAUGHER);
		sc.switchToGame(e);
		updatePlayerUI(game.getPlayer());
		updateOppUI(game.getOpponent());
		System.out.println("Your Monster is: "+game.getPlayer().getName());
		boardUI();
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
		playerTxt.setEditable(false);
		StringBuilder p = new StringBuilder();
		 p.append( "Player: " + player.getName()+"\nEnergy: "+ player.getEnergy()+ "\nOriginal Role: " + player.getOriginalRole());
		if(player.isConfused())
				p.append( "\nCurrent Role: " + player.getRole()+ "\nConfusion turns left: "+ player.getConfusionTurns());		
		if(player.isFrozen())
			p.append("\nFrozen");
		if(player.isShielded())
			p.append("\nShield");
		playerTxt.setText(p.toString());
	}
	public void updateOppUI(Monster opp)
	{
		oppTxt.setEditable(false);
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
