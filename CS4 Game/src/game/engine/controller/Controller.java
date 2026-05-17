package game.engine.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Controller {
	
	private Stage stage;
	
	private boolean isExceptionActive;
	private Game game;
	public static String tempWord;

	
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
	private ImageView player;
	
	@FXML
	private ImageView opponent;

	@FXML
	private ImageView player1;

	@FXML
	private ImageView player2;
	
	@FXML
	private GridPane boardGrid;

	private StackPane[] cells;
	
	@FXML
	private StackPane cards ;
	@FXML
	private StackPane cardsDrawn;
	private static Card Currcard;
	
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
			return createSockCell(i);
		else if (c instanceof CardCell)
			r = new Rectangle(60, 60, Color.DARKRED);

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

	private StackPane createSockCell(int i) {
		
			StackPane sp = new StackPane();

			Label indexLabel = new Label(i + " ");
			indexLabel.setTranslateX(-17);
			indexLabel.setTranslateY(-20);

			ImageView sockImage = createSockImageView();
			if (sockImage != null)
				sp.getChildren().addAll(sockImage, indexLabel);
			else
				sp.getChildren().addAll(new Rectangle(60, 60, Color.ORANGE), indexLabel);

			return sp;
		}

		private ImageView createSockImageView() {
			String image = "ContaminationSock.jpeg";

			ImageView sockImage = new ImageView(new Image(getClass().getResource(image).toExternalForm()));
			sockImage.setFitWidth(60);
			sockImage.setFitHeight(60);
			sockImage.setPreserveRatio(false);
			return sockImage;
		}


	public void chooseScarer(ActionEvent e) throws IOException {
		game = new Game(Role.SCARER);
		sc.switchToGame(e, game);
		setGame(game);
		System.out.println("Your Monster is: " + game.getPlayer().getName());
		 initializePlayerIcon(Role.SCARER);
		 initializeOppIcon(Role.LAUGHER);
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
		 initializePlayerIcon(Role.LAUGHER);
		 initializeOppIcon(Role.SCARER);
	}
	@FXML
	public void rollUI(ActionEvent l) throws InvalidMoveException, IOException {
		try {
			if(!isExceptionActive)
			{
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
			
			updateIcons();
			if(curr.isConfused())
				{
				confusedMonsterImage(game.getPlayer());
				confusedMonsterImage(game.getOpponent());
				}
				
			if(game.getWinner()!=null)
				getWinText(l);
			if(curr==game.getPlayer())
				s= "Player 2";
			else
				s= "Player 1";
			
			
			updateConsole("\n"+ (++counter) +"- "+ s +"'s turn: ");
			}
		} catch (InvalidMoveException e) {
			updatePlayerUI(game.getPlayer());
			updateOppUI(game.getOpponent());
			resetPos(game.getCurrent().getPosition(), game.getCurrent());
			displayInvalidMoveException("Invalid Move", "Cannot land on the same cell as your opponent!");
			
		}
	}


	

	public void resetPos(int pos, Monster curr)
	{
		ImageView s;
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
		ImageView s;
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
		ImageView s;
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

	public void updateMonsterCells()
	{
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			Cell c = game.getBoard().getBoardCells()[indexToRowCol2(i)[0]][indexToRowCol2(i)[1]];
			if(c instanceof MonsterCell)
			{
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
		}
	}
	
	@FXML
	public void usePowerUpUI(ActionEvent l) {
		try {
			if(!isExceptionActive)
			{
			game.usePowerup();
			updateOppUI(game.getOpponent());
			updatePlayerUI(game.getPlayer());
			updateConsole("Used PowerUp!");
		}
		} catch (Exception e) {
			

			displayNotEnoughEnergyException("Not Enough Energy!", "You need 500 energy to use a powerUp!");
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
			{
			p.append("MultiTasker");
			if(((MultiTasker)player).getNormalSpeedTurns()!=0)
			p.append("\nPower Up ACTIVATED: \nNormal speed for: "+ ((MultiTasker)player).getNormalSpeedTurns()+ " turns");
			}
		if (player instanceof Schemer)
			p.append("Schemer");
		if (player instanceof Dasher)
			{
			p.append("Dasher");
			if(((Dasher)player).getMomentumTurns()!=0)
				p.append("\nPower Up ACTIVATED: \nIncreased momentum for:  "+ ((Dasher)player).getMomentumTurns()+ " turns");
			}
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
		console.positionCaret(console.getText().length());
		
	}
	public void updateConsole2()
	{
		console.appendText(tempWord);
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
		cards.getChildren().clear();
		Label l = new Label("25");
		l.setTextFill(Color.WHITE);
		for (int i = 0; i < 25; i++) {
			
			ImageView cardBack = createCardBackView();
			cardBack.setTranslateX(-i-0.5);
			cards.getChildren().add(cardBack);
		}
		l.setTranslateY(30);
		cards.getChildren().add(l);
	}

	private ImageView createCardBackView() {
		String image = "CardBack.jpeg";

		ImageView cardBack = new ImageView(new Image(getClass().getResource(image).toExternalForm()));
		cardBack.fitWidthProperty().bind(cards.widthProperty());
		cardBack.fitHeightProperty().bind(cards.heightProperty());
		cardBack.setPreserveRatio(false);
		return cardBack;
	}
	private void createCardFrontView() {
		String image;
		String cardDesc="";
		switch(Currcard.getName())
		{
		case "Position Swap": image = "SwapperCard.jpg"; cardDesc=""+Currcard.getDescription(); break;
		case "Contamination Code": image = "StartOverCard.jpg";cardDesc=""+Currcard.getDescription(); break;
		case "2319 Alert": image = "StartOverCard.jpg"; cardDesc=""+Currcard.getDescription();  break;
		case "Small Snatcher": image = "EnergyStealCard.jpg"; cardDesc=""+Currcard.getDescription(); break;
		case "Sneaky Thief": image = "EnergyStealCard.jpg"; cardDesc=""+Currcard.getDescription(); break;
		case "Mega Drain": image = "EnergyStealCard.jpg"; cardDesc=""+Currcard.getDescription(); break;
		case "Super Shield": image = "ShieldCard.jpg"; cardDesc=""+Currcard.getDescription(); break;
		case "Mind Scramble": image = "ConfusionCard.jpg"; cardDesc=""+Currcard.getDescription(); break;
		case "Total Confusion": image = "ConfusionCard.jpg"; cardDesc=""+Currcard.getDescription(); break;
		default: image = "CardBack.jpeg"; 
		}
		
		TextArea t = new TextArea(cardDesc);
		StackPane sp =  new StackPane(t);
		sp.setScaleX(0.85); sp.setScaleY(0.4); sp.setTranslateY(27);
		t.setWrapText(true);
		t.setEditable(false);
		ImageView cardFront = new ImageView(new Image(getClass().getResource(image).toExternalForm()));
		cardFront.fitWidthProperty().bind(cardsDrawn.widthProperty());
		cardFront.fitHeightProperty().bind(cardsDrawn.heightProperty());
		cardFront.setPreserveRatio(false);
		cardsDrawn.getChildren().addAll(cardFront,sp);
		cardsDrawn.setScaleX(1.3);cardsDrawn.setScaleY(1.2);
	}

	public void drawCardUI()
	{
		if(cards.getChildren().size()!=2)
		{ Label l = (Label)cards.getChildren().remove(cards.getChildren().size()-1);
		String s = l.getText();
		s= ""+ (Integer.parseInt(s)-1);
			cards.getChildren().remove(cards.getChildren().size()-1);
			l.setText(s);
			cards.getChildren().add(l);
		}
		else
			
			{
			cards.getChildren().remove(1);
			initializeCardsUI();
			drawCardUI();
			}
		 createCardFrontView();
		
	}
	

	public void setCard(Card c)
	{
		Currcard=c;
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
			updateMonsterCells();
			updateConsole("\nYou landed on Monster Cell " + pos);
			}
		else if(c instanceof CardCell)
		{
			updateConsole("\nYou landed on Card Cell " + pos);
			drawCardUI();
			updateConsole2();

		}
		
		else if(c instanceof Cell )
		{
			updateConsole("\nYou landed on Cell " + pos);
		}
	}

	public static void setTempWord(String s) {
		tempWord = s;
	}
	
	
    private void displayInvalidMoveException(String title, String message) {
    	if(!isExceptionActive)
    	{
    		isExceptionActive=true;
    	Stage alertStage = new Stage();
        alertStage.setTitle(title);

        Label label = new Label(message);
        Button closeButton = new Button("Roll Again");
        closeButton.setOnAction( new EventHandler<ActionEvent>()
        		{
        		public void handle(ActionEvent e)
        		{
        			isExceptionActive=false;
        			alertStage.close();
        		}
        
        		});

        //Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        BorderPane pane = new BorderPane();
        pane.setTop(label);
        pane.setCenter(closeButton);

        Scene scene = new Scene(pane,500,100);
        alertStage.setScene(scene);
        alertStage.show();
    }}
    private void displayNotEnoughEnergyException(String title, String message) {
        if(!isExceptionActive)
        {
    	isExceptionActive=true;
    	Stage alertStage = new Stage();
        alertStage.setTitle(title);

        Label label = new Label(message);
        Button closeButton = new Button("Got it!");
        closeButton.setOnAction( new EventHandler<ActionEvent>()
        		{
        		public void handle(ActionEvent e)
        		{
        			isExceptionActive=false;
        			alertStage.close();
        		}
        
        		});
     
        BorderPane pane = new BorderPane();
        pane.setTop(label);
        pane.setCenter(closeButton);

        Scene scene = new Scene(pane, 500, 100);
        alertStage.setScene(scene);
        alertStage.show();
    }}
    
    @FXML
    public void displayMonsterTypes(ActionEvent event) throws IOException {
        
    	Stage alertStage = new Stage();
    	Parent root = FXMLLoader.load(getClass().getResource("MonsterTypes.fxml"));
        Scene scene = new Scene(root);
        alertStage.setScene(scene);
        alertStage.show();
    
}
    
    
    
  public void switchToWinScreen(KeyEvent e) throws IOException
   {
	  sc.setStage(getStage());
  	if(game.getPlayer().getEnergy()>= game.getOpponent().getEnergy())
	sc.switchToGameOver(e,"YOU WON!"+ "\nPlayer Energy: " + game.getPlayer().getEnergy()+ "\nOpponent Energy: "+ game.getOpponent().getEnergy());
  	else
	sc.switchToGameOver(e,"YOU LOSE!"+ "\nPlayer Energy: " + game.getPlayer().getEnergy()+ "\nOpponent Energy: "+ game.getOpponent().getEnergy());
   }
    
    public void addEnergy()
    {
    	game.getCurrent().setEnergy(game.getCurrent().getEnergy()+100);
    	updateOppUI(game.getOpponent()); 
		updatePlayerUI(game.getPlayer());
    }

	public Stage getStage() {
		
		return stage;
	}
	public void setStage( Stage s)
	{
		stage= s;
	}

	public boolean isExceptionActive() {
		return isExceptionActive;
	}

	public void setExceptionActive(boolean isExceptionActive) {
		this.isExceptionActive = isExceptionActive;
	}

	public void initializeMonsterImages() {
		initializePlayerIcon(game.getPlayer().getOriginalRole());
		initializeOppIcon(game.getOpponent().getOriginalRole());
	}

	public void initializePlayerIcon(Role role) {
		String theImage = null;
		if(role.equals(Role.SCARER))
		{
			switch(game.getPlayer().getName())
			{
			case "James P. Sullivan": theImage = "Sullivan.jpeg";
				break;
			case"Randall Boggs": theImage = "RandallOpp.jpeg";
				break;
			case "Roz": theImage = "RozOpp.jpeg";
				break;
			case "Henry J. Waternoose": theImage = "WaternooseOpp.jpeg";
				break;
		
			}
		}
		else
		{
			switch(game.getPlayer().getName())
		
			{
			case "Mike Wazowski": theImage = "Wazowski.jpeg";
				break;
			case"Celia Mae": theImage = "Celia.jpeg";
				break;
			case "Fungus": theImage = "Fungus.jpeg";
				break;
			case "Yeti": theImage = "Yeti.jpeg";
				break;
			
		
			}
		}
		Image playerImage = new Image(getClass().getResource(theImage).toExternalForm());
		player.setImage(playerImage);
		player1.setImage(playerImage);
		}
		

	public void initializeOppIcon(Role role) {
		String theImage = null;
		if(role.equals(Role.SCARER))
		{
			switch(game.getOpponent().getName())
			{
			case "James P. Sullivan": theImage = "Sullivan.jpeg";
				break;
			case"Randall Boggs": theImage = "RandallOpp.jpeg";
				break;
			case "Roz": theImage = "RozOpp.jpeg";
				break;
			case "Henry J. Waternoose": theImage = "WaternooseOpp.jpeg";
				break;
		
			}
		}
		else
		{
			switch(game.getOpponent().getName())
		
			{
			case "Mike Wazowski": theImage = "Wazowski.jpeg";
				break;
			case"Celia Mae": theImage = "Celia.jpeg";
				break;
			case "Fungus": theImage = "Fungus.jpeg";
				break;
			case "Yeti": theImage = "Yeti.jpeg";
				break;
		
		
			}
		}
		Image oppImage = new Image(getClass().getResource(theImage).toExternalForm());
		opponent.setImage(oppImage);
		player2.setImage(oppImage);

		}
		
	
	public void updateIcons()
	{
		initializePlayerIcon(game.getPlayer().getRole());
		initializeOppIcon(game.getOpponent().getRole());
	}
	
	public void getJavaConsole(){
		OutputStream os = new OutputStream(){
			public void write(int arg0) throws IOException {
				console.appendText(""+(char)arg0);
				Platform.runLater(()->{
					console.positionCaret(console.getText().length());
					console.setScrollTop(Double.MAX_VALUE);
				});
			}
			
		};
		System.setOut(new PrintStream(os,true));
		System.setErr(new PrintStream(os,true));

		
	}
	private void confusedMonsterImage(Monster monster) {
		String theImage = null;
		if(monster.getRole().equals(Role.SCARER))
		{
			switch(monster.getName())
			{
			case "James P. Sullivan": theImage = "SullivanPlayer.jpeg";
				break;
			case"Randall Boggs": theImage = "Randall.jpeg";
				break;
			case "Roz": theImage = "Roz.jpeg";
				break;
			case "Henry J. Waternoose": theImage = "Waternoose.jpeg";
				break;
			}
				
		}
		else
		{	
			switch(monster.getName())
			{
			case "Mike Wazowski": theImage = "WazowskiOpp.jpeg";
				break;
			case"Celia Mae": theImage = "CeliaOpp.jpeg";
				break;
			case "Fungus": theImage ="FungusOpp.jpeg";
				break;
			case "Yeti": theImage = "YetiOpp.jpeg";
				break;
			}
			}
		Image monsterImage = new Image(getClass().getResource(theImage).toExternalForm());
		if(monster.equals(game.getPlayer()))
			player.setImage(monsterImage);
		else
			opponent.setImage(monsterImage);
		
		
		
	}
	
	
 
	
}