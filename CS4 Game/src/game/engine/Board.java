package game.engine;

import java.util.*;

import game.engine.cards.Card;
import game.engine.cards.SwapperCard;
import game.engine.cells.*;
import game.engine.dataloader.DataLoader;
import game.engine.monsters.Monster;
import game.engine.exceptions.*;

import java.io.*;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		cards = new ArrayList<Card>();
		
		this.setCardsByRarity();
		reloadCards();
	}
	
	public Cell[][] getBoardCells() {
		return boardCells;
	}
	
	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	
	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}

	public static ArrayList<Card> getOriginalCards() {
		return originalCards;
	}
	
	public static ArrayList<Card> getCards() {
		return cards;
	}
	
	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}
	
	private int[] indexToRowCol(int index)
	{
		int row,col;
		int[] x = new int[2];
		if((index/10)%2==0)
		{
			row = index/10;
			col = index%10;
			
		}
		else
		{
			row= index/10;
			col = 9- (index%10);
		}
		x[0]= row;
		x[1]=col;
		return x;
	}
	
	private Cell getCell(int index)
	{
		
		int[] x = indexToRowCol(index);
		return boardCells[x[0]][x[1]];
		
	}
	
	private void setCell(int index, Cell cell)
	{
		int[] x = indexToRowCol(index);
		boardCells[x[0]][x[1]]= cell;
	}
	
	
	public void initializeBoard(ArrayList<Cell> specialCells) throws IOException
	{
		ArrayList<ConveyorBelt> conveyors = new ArrayList<>();
		ArrayList<ContaminationSock> contaminations = new ArrayList<>();
		ArrayList<DoorCell> doors = new ArrayList<>();
		
		for (int i = 0; i < Constants.MONSTER_CELL_INDICES.length; i++) {
			
			MonsterCell MC= new MonsterCell(stationedMonsters.get(i).getName(), stationedMonsters.get(i));
			setCell(Constants.MONSTER_CELL_INDICES[i],MC);
		}
		
		for (int i = 0; i < Constants.CARD_CELL_INDICES.length; i++) 
			setCell(Constants.CARD_CELL_INDICES[i],new CardCell("Card Cell"));
		
		while(!specialCells.isEmpty()) {
			if(specialCells.get(0) instanceof ConveyorBelt)
				conveyors.add((ConveyorBelt)specialCells.remove(0));
			else{
				
			
			if(specialCells.get(0) instanceof ContaminationSock)
				contaminations.add((ContaminationSock)specialCells.remove(0));
			else
				if(specialCells.get(0) instanceof DoorCell)
					doors.add((DoorCell)specialCells.remove(0));
			}
		}
		for (int i = 0; i < Constants.CONVEYOR_CELL_INDICES.length; i++) {
			setCell(Constants.CONVEYOR_CELL_INDICES[i],conveyors.remove(0));
		}
		for (int i = 0; i < Constants.SOCK_CELL_INDICES.length; i++) {
			setCell(Constants.SOCK_CELL_INDICES[i],contaminations.remove(0));
		}
		
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			if(i%2==1 && getCell(i).getClass()==null)
				setCell(i, doors.remove(0));
			else
				if(i%2==0 && getCell(i).getClass()==null)
				setCell(i,new Cell("Normal Cell"));
		}
	}
	
	private void setCardsByRarity()
	{
		ArrayList<Card> newList=new ArrayList<>();
		while(!originalCards.isEmpty())
		{
			Card x = originalCards.remove(0);
			for (int i = 0; i < x.getRarity(); i++) {
				newList.add(x);
			}
		}
		originalCards=newList;
	}
	
	
	public static void reloadCards()
	{
		ArrayList<Card> x = originalCards;
		Collections.shuffle(x);
		cards = x;
	}

	
	public static Card drawCard()
	{
		if(cards.isEmpty())
			reloadCards();
		return cards.remove(0);
	}
	
	private void updateMonsterPositions(Monster Player, Monster Opponent)
	{
		int playerPosition = Player.getPosition();
		Cell playerCell = getCell(playerPosition);
		int opponentPosition =Opponent.getPosition();
		Cell opponentCell = getCell(opponentPosition);
		for (int i = 0; i < Constants.BOARD_ROWS; i++) {
			for (int j = 0; j < Constants.BOARD_COLS; j++) {
				Cell c = boardCells[i][j];
				if(c.getMonster().equals(Player) || 
						c.getMonster().equals(Opponent))
					c.setMonster(null);
			
					
			}
			
		}
		setCell(playerPosition, playerCell);
		setCell(opponentPosition,opponentCell);
	}
	
	public void moveMonster(Monster currentMonster, int roll, Monster opponentMonster) throws InvalidMoveException{
		int initialPosition = currentMonster.getPosition();
		int initalConfusionState = currentMonster.getConfusionTurns();
		currentMonster.move(roll);
		if(currentMonster.getPosition()==opponentMonster.getPosition())
		{
			currentMonster.setPosition(initialPosition);
			throw new InvalidMoveException();
		}
		else
		{
			getCell(currentMonster.getPosition()).onLand(currentMonster, opponentMonster);
			if(currentMonster.getPosition()==opponentMonster.getPosition()) // just in case a transport occurs
			{
				currentMonster.setPosition(initialPosition);
				throw new InvalidMoveException();
			}
			else
			{
				int confusion = currentMonster.getConfusionTurns();
				if(currentMonster.isConfused() && confusion== initalConfusionState)
				{
					currentMonster.decrementConfusion();
					opponentMonster.decrementConfusion();
				}
			}
		}
		updateMonsterPositions(currentMonster, opponentMonster);
	}
}
