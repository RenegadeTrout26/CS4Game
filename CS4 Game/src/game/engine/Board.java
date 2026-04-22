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
		row = index/10;
		if(row%2==0)
		{
		
			col = index%10;
			
		}
		else
		{
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
		
		for (int i = 0; i < Constants.MONSTER_CELL_INDICES.length && !stationedMonsters.isEmpty(); i++) {
			
			MonsterCell MC= new MonsterCell(stationedMonsters.get(i).getName(), stationedMonsters.get(i));
			setCell(Constants.MONSTER_CELL_INDICES[i],MC);
			stationedMonsters.get(i).setPosition(Constants.MONSTER_CELL_INDICES[i]);
		}
		
		for (int i = 0; i < Constants.CARD_CELL_INDICES.length ; i++) 
			setCell(Constants.CARD_CELL_INDICES[i],new CardCell("Card Cell"));
		
		for(int i = 0; i<specialCells.size(); i++) {
			if(specialCells.get(i) instanceof ConveyorBelt)
				conveyors.add((ConveyorBelt)specialCells.get(i));
			else{
				
			
			if(specialCells.get(i) instanceof ContaminationSock)
				contaminations.add((ContaminationSock)specialCells.get(i));
			else
				if(specialCells.get(0) instanceof DoorCell)
					doors.add((DoorCell)specialCells.get(i));
			}
		}
		for (int i = 0; i < Constants.CONVEYOR_CELL_INDICES.length && !conveyors.isEmpty(); i++) {
			setCell(Constants.CONVEYOR_CELL_INDICES[i],conveyors.remove(0)); 
		}
		for (int i = 0; i < Constants.SOCK_CELL_INDICES.length && !contaminations.isEmpty(); i++) {
			setCell(Constants.SOCK_CELL_INDICES[i],contaminations.remove(0));
		}
		
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			if(!ArrayContains(Constants.CARD_CELL_INDICES,i) && !ArrayContains(Constants.CONVEYOR_CELL_INDICES,i) && !ArrayContains(Constants.MONSTER_CELL_INDICES, i) && !ArrayContains(Constants.SOCK_CELL_INDICES, i) && getCell(i)== null)
			{
				if(i%2==1 && !doors.isEmpty())
				{
					setCell(i, doors.remove(0));
					
				}
				else
					setCell(i,new Cell("Normal Cell"));
			}
		}
	}
private static boolean ArrayContains(int[] arr, int index)
{
	for (int i = 0; i < arr.length; i++) {
		if(arr[i]==index)
			return true;
	}
	return false;
}
//public void initializeBoard(ArrayList<Cell> specialCells) throws IOException{
//	
//		
//		ArrayList<Integer> conveyors = new ArrayList<>();
//		ArrayList<Integer> socks = new ArrayList<>();
//		ArrayList<Integer> monsters = new ArrayList<>();
//		ArrayList<Integer> cards = new ArrayList<>();
//		ArrayList<DoorCell> doors = new ArrayList<>();
//		
//		
//		
//		for (int j = 0; j < Constants.CONVEYOR_CELL_INDICES.length; j++) {
//			conveyors.add(Constants.CONVEYOR_CELL_INDICES[j]);
//		}
//		for (int j = 0; j < Constants.SOCK_CELL_INDICES.length; j++) {
//			socks.add(Constants.SOCK_CELL_INDICES[j]);
//		}
//		for (int j = 0; j < Constants.MONSTER_CELL_INDICES.length; j++) {
//			monsters.add(Constants.MONSTER_CELL_INDICES[j]);
//		}
//		for (int j = 0; j < Constants.CARD_CELL_INDICES.length; j++) {
//			cards.add(Constants.CARD_CELL_INDICES[j]);
//		}
//		
//		
//		
//		while(!specialCells.isEmpty()) {
//			
//			Cell c =specialCells.remove(0); 
//			if(c instanceof ConveyorBelt)
//			{
//				setCell(conveyors.remove(0),c);
//				
//			}
//			else if(c instanceof ContaminationSock)
//			{
//			
//				setCell(socks.remove(0),c);
//			}
//			else
//				if(c instanceof DoorCell)
//					doors.add((DoorCell)c);	
//		}
//	
//		while(!monsters.isEmpty())
//		{
//			setCell(monsters.remove(0), new MonsterCell(stationedMonsters.get(0).getName(), stationedMonsters.remove(0)));
//		}
//		while(!cards.isEmpty())
//		{
//			setCell(cards.remove(0), new CardCell("Card Cell"));
//		}
//		
//		for (int i = 0; i <Constants.BOARD_SIZE; i++) {
//			if(!cards.contains(i) && !conveyors.contains(i) && !socks.contains(i) && !monsters.contains(i) && getCell(i)== null)
//			{if(i%2==0)
//				{setCell(i, new Cell("Normal Cell")); }
//			else
//				{setCell(i, doors.remove(0));}
//		
//			}
//		}
//
//}

public static void main(String[] args) throws IOException {
	Board b = new Board(DataLoader.readCards());
	setStationedMonsters(DataLoader.readMonsters());
	b.initializeBoard(DataLoader.readCells());
	Cell c = new Cell("mohsen");
	b.setCell(30, c);
	
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
