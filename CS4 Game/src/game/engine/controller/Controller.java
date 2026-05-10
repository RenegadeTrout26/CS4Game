package game.engine.controller;

import java.io.IOException;

import game.engine.*;
import game.engine.cards.Card;
import game.engine.cells.CardCell;
import game.engine.cells.Cell;
import game.engine.cells.ContaminationSock;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.DoorCell;
import game.engine.cells.MonsterCell;
import game.engine.exceptions.*;
import game.engine.monsters.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Controller {

	private Game game;
	@FXML
	private Rectangle player;
	@FXML
	private Circle opponent;
	
	private SceneController sc = new SceneController();

	@FXML
	private TextArea playerTxt;
	@FXML
	private TextArea console;
	private int counter=1;

	@FXML
	private ProgressBar playerEnergy;
	
	@FXML
	private ProgressBar oppEnergy;

	@FXML
	private TextArea oppTxt;

	@FXML
	private GridPane boardGrid;

	private StackPane[] cells;
	
	@FXML
	private StackPane cards ;
	
	public static int  roll;

	public void boardUI() {
		cells = new StackPane[100];
	
		
		for (int i = 0; i < cells.length; i++) {
			StackPane cell = createCell(i);
			cells[i] = cell;
			boardGrid.add(cell, indexToRowCol(i)[1], indexToRowCol(i)[0]);
		}
		cells[0].getChildren().addAll(player, opponent);
	}

	private int[] indexToRowCol(int index) {
		int cols = Constants.BOARD_COLS;
		int row = index / cols;
		int col = index % cols;
		if (row % 2 == 1)
			col = cols - 1 - col;
		row = (Constants.BOARD_ROWS - 1) - row;
		return new int[] { row, col };
	}

	private int[] indexToRowCol2(int index) {
		int cols = Constants.BOARD_COLS;

		int row = index / cols;
		int col = index % cols;

		if (row % 2 == 1)
			col = cols - 1 - col;

		return new int[] { row, col };
	}

	private StackPane createCell(int i) {
		Cell c = game.getBoard().getBoardCells()[indexToRowCol2(i)[0]][indexToRowCol2(i)[1]];
		Rectangle r;
		Label l2 = new Label("");
		if (c instanceof ConveyorBelt)
			r = new Rectangle(60, 60, Color.GREEN);
		else if (c instanceof ContaminationSock)
			r = new Rectangle(60, 60, Color.ORANGE);
		else if (c instanceof CardCell)
			r = new Rectangle(60, 60, Color.RED);

		else if (c instanceof MonsterCell)
		{
			r = new Rectangle(60, 60, Color.LIGHTBLUE);	
			l2 = new Label(""+((MonsterCell)c).getCellMonster().getEnergy());
			if(((MonsterCell)c).getCellMonster().getRole().equals(Role.LAUGHER))
			l2.setTextFill(Color.BLUE);
			else
				l2.setTextFill(Color.INDIANRED);
		}
		else if (c instanceof DoorCell){
			r = new Rectangle(60, 60, Color.MEDIUMPURPLE);
			l2 = new Label(""+((DoorCell)c).getEnergy());
			if(((DoorCell)c).getRole().equals(Role.LAUGHER))
			l2.setTextFill(Color.BLUE);
			else
				l2.setTextFill(Color.RED);
		}
		else
			r = new Rectangle(60, 60, Color.YELLOW);
		Label l = new Label(i + " ");
		l.setTranslateX(-17); l.setTranslateY(-20);
		StackPane sp = new StackPane();
		if(c instanceof DoorCell || c instanceof MonsterCell)
			sp.getChildren().addAll(r, l,l2);
		else
			sp.getChildren().addAll(r, l);
		return sp;
	}

	@FXML
	public void chooseScarer(ActionEvent e) throws IOException {
		game = new Game(Role.SCARER);
		sc.switchToGame(e, game);
		setGame(game);
		System.out.println("Your Monster is: " + game.getPlayer().getName());

	}

	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}

	@FXML
	public void chooseLaugher(ActionEvent e) throws IOException {
		game = new Game(Role.LAUGHER);
		sc.switchToGame(e, game);
		setGame(game);
		System.out.println("Your Monster is: " + game.getPlayer().getName());
	}

	@FXML
	public void rollUI(ActionEvent l) throws InvalidMoveException, IOException {
		try {
			Monster curr = game.getCurrent();
			int ogPos = game.getCurrent().getPosition();
			String s;
			
			
			clearOldPos(ogPos,game.getCurrent());
			updatePlayerUI(game.getPlayer());
			updateOppUI(game.getOpponent());
			
			
			
			game.playTurn();
			
			updateConsole( "\nDice Roll: "+Controller.roll);
			updatePlayerUI(game.getPlayer());
			updateOppUI(game.getOpponent());
			moveUI(curr);
			
			
			if(game.getWinner()!=null)
				getWinText(l);
			if(curr==game.getPlayer())
				s= "Player 2";
			else
				s= "Player 1";
			
			
			updateConsole("\n"+ (++counter) +"- "+ s +"'s turn: ");
			
		} catch (InvalidMoveException e) {
			updatePlayerUI(game.getPlayer());
			updateOppUI(game.getOpponent());
			resetPos(game.getCurrent().getPosition(), game.getCurrent());
			e.printStackTrace();
			
		}
	}
	public void resetPos(int pos, Monster curr)
	{
		Shape s;
		if(curr.equals(game.getPlayer()))
		{
			s = player;
		}
		else
			s= opponent;
		StackPane sp = cells[pos];
		sp.getChildren().add(s);
	}
	
	public  void clearOldPos(int ogPos,Monster curr)
	{
		Shape s;
		if(curr.equals(game.getPlayer()))
		{
			s = player;
		}
		else
			s= opponent;
		StackPane sp = cells[ogPos];
		sp.getChildren().remove(s);
	}
	public void moveUI(Monster curr) throws IOException
	{
		Shape s;
		if(curr.equals(game.getPlayer()))
		{
			s = player;
		}
		else
			s= opponent;
		
		int pos =curr.getPosition();
		
		
		int[] x= indexToRowCol2(pos);
	
		onLandUI(pos,game.getBoard().getBoardCells()[x[0]][x[1]]);
		StackPane sp = cells[pos];
		sp.getChildren().add(s);
	
	}
	public void updateDoorCells(int i)
	{
		StackPane sp = cells[i];
		sp.getChildren().removeAll();
		Rectangle r = new Rectangle(60, 60, Color.YELLOW);
		Label l = new Label(i + " ");
		l.setTranslateX(-17); l.setTranslateY(-20);
		sp.getChildren().addAll(r, l);
	}
	public static void setRoll(int rol)
	{
		Controller.roll=rol;
	}
	public void updateMonsterCells(int i)
	{
		Cell c = game.getBoard().getBoardCells()[indexToRowCol2(i)[0]][indexToRowCol2(i)[1]];
		StackPane sp = cells[i];
		sp.getChildren().removeAll();
		Label l2;
		Rectangle r = new Rectangle(60, 60, Color.LIGHTBLUE);	
		l2 = new Label(""+((MonsterCell)c).getCellMonster().getEnergy());
		if(((MonsterCell)c).getCellMonster().getRole().equals(Role.LAUGHER))
		l2.setTextFill(Color.BLUE);
		else
			l2.setTextFill(Color.INDIANRED);
		
		Label l = new Label(i + " ");
		l.setTranslateX(-17); l.setTranslateY(-20);
		sp.getChildren().addAll(r, l,l2);
	}
	
	@FXML
	public void usePowerUpUI(ActionEvent l) {
		try {
			game.usePowerup();
			updateOppUI(game.getOpponent());
			updatePlayerUI(game.getPlayer());
			updateConsole("Used PowerUp!");
		} catch (Exception e) {
			

			e.printStackTrace();
		}
	}

	public void updatePlayerUI(Monster player) {
		StringBuilder p = new StringBuilder();
		p.append("Player 1: " + player.getName() + "\nEnergy: "
				+ player.getEnergy() + "\nOriginal Role: "
				+ player.getOriginalRole() + "\nPosition: "
				+ player.getPosition() + "\nMonster Type: ");
		if (player instanceof Dynamo)
			p.append("Dynamo");
		if (player instanceof MultiTasker)
			p.append("MultiTasker");
		if (player instanceof Schemer)
			p.append("Schemer");
		if (player instanceof Dasher)
			p.append("Dasher");
		if (player.isConfused())
			p.append("\nCurrent Role: " + player.getRole()
					+ "\nConfusion turns left: " + player.getConfusionTurns());
		if (player.isFrozen())
			p.append("\nFrozen");
		if (player.isShielded())
			p.append("\nShield");

		playerTxt.setText(p.toString());
		playerTxt.setEditable(false);
		
		double en = player.getEnergy();
		if(en==0)
			playerEnergy.setProgress(0);
		else
			playerEnergy.setProgress(en/Constants.WINNING_ENERGY);
	
	}

	public void updateOppUI(Monster opp) {
		StringBuilder s = new StringBuilder();
		s.append("Player 2: " + opp.getName() + "\nEnergy: " + opp.getEnergy()
				+ "\nOriginal Role: " + opp.getOriginalRole() + "\nPosition: "
				+ opp.getPosition() + "\nMonster Type: ");
		if (opp instanceof Dynamo)
			s.append("Dynamo");
		if (opp instanceof MultiTasker)
			s.append("MultiTasker");
		if (opp instanceof Schemer)
			s.append("Schemer");
		if (opp instanceof Dasher)
			s.append("Dasher");
		if (opp.isConfused())
			s.append("\nCurrent Role: " + opp.getRole()
					+ "\nConfusion turns left: " + opp.getConfusionTurns());
		if (opp.isFrozen())
			s.append("\nFrozen");
		if (opp.isShielded())
			s.append("\nShield");
		oppTxt.setText(s.toString());
		oppTxt.setEditable(false);
		
		double en = opp.getEnergy();
		if(en==0)
			oppEnergy.setProgress(0);
		else
			oppEnergy.setProgress(en/Constants.WINNING_ENERGY);
	}
	public void updateConsole(String s)
	{

		console.appendText(s);
		
	}
	public void getWinText(ActionEvent e) throws IOException
	{
	
			if(game.getWinner()== game.getPlayer())
				sc.switchToGameOver(e,"YOU WON!"+ "\nPlayer Energy: " + game.getPlayer().getEnergy()+ "\nOpponent Energy: "+ game.getOpponent().getEnergy());
			else
				sc.switchToGameOver(e,"YOU LOSE!"+ "\nPlayer Energy: " + game.getPlayer().getEnergy()+ "\nOpponent Energy: "+ game.getOpponent().getEnergy());
	}
	@FXML
	public void mainMenuButton(ActionEvent e) throws IOException

	{
		sc.switchToStartMenu(e);
	}
	
	public void initializeCardsUI()
	{
		for (int i = 0; i < 25; i++) {
			Rectangle r = new Rectangle(90,150,Color.AQUA);
			r.setStroke(Color.BLACK);
			r.setTranslateX(-i+0.5);
			cards.getChildren().add(r);
		}
	}
	public void drawCardUI()
	{
		if(cards.getChildren().size()!=1)
		cards.getChildren().remove(cards.getChildren().size()-1);
		else
			initializeCardsUI();
	}
	
	public void onLandUI(int pos, Cell c)
	{
		if(c instanceof DoorCell)
			
			{
			updateDoorCells(pos);
			updateConsole("\nYou landed on Door Cell " + pos);
			}
		else if(c instanceof MonsterCell)
			{
			updateMonsterCells(pos);
			updateConsole("\nYou landed on Monster Cell " + pos);
			}
		else if(c instanceof CardCell)
		{
			drawCardUI();
			updateConsole("\nYou landed on Card Cell " + pos);
		}
		
		else if(c instanceof Cell )
		{
			updateConsole("\nYou landed on Cell " + pos);
		}
	}
}