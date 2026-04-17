package game.engine;

import java.util.ArrayList;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.monsters.Monster;
import game.engine.dataloader.*;

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
	
	
	void initializeBoard(ArrayList<Cell> specialCells) throws IOException
	{
		ArrayList<Cell> typeOfCells = DataLoader.readCells();
		ArrayList<ConveyorBelt> conveyors = new ArrayList<>();
		ArrayList<ContaminationSock> contaminations = new ArrayList<>();
		ArrayList<DoorCell> doors = new ArrayList<>();
		
		for (int i = 0; i < Constants.MONSTER_CELL_INDICES.length; i++) {
			
			MonsterCell MC= new MonsterCell(stationedMonsters.get(i).getName(), stationedMonsters.get(i));
			setCell(Constants.MONSTER_CELL_INDICES[i],MC);
		}
		
		for (int i = 0; i < Constants.CARD_CELL_INDICES.length; i++) {
			
			CardCell CC= new CardCell(originalCards.get(i).getName());
			setCell(Constants.CARD_CELL_INDICES[i],CC);
		}
		while(!typeOfCells.isEmpty()) {
			if(typeOfCells.get(0) instanceof ConveyorBelt)
				conveyors.add((ConveyorBelt)typeOfCells.remove(0));
			else{
				
			
			if(typeOfCells.get(0) instanceof ContaminationSock)
				contaminations.add((ContaminationSock)typeOfCells.remove(0));
			else
				if(typeOfCells.get(0) instanceof DoorCell)
					doors.add((DoorCell)typeOfCells.remove(0));
				else
					typeOfCells.remove(0);
			}
		}
		for (int i = 0; i < Constants.CONVEYOR_CELL_INDICES.length; i++) {
			setCell(Constants.CONVEYOR_CELL_INDICES[i],conveyors.remove(i));
		}
		for (int i = 0; i < Constants.SOCK_CELL_INDICES.length; i++) {
			setCell(Constants.SOCK_CELL_INDICES[i],contaminations.remove(i));
		}
		
		for (int i = 0; i < boardCells.length; i++) {
			if(i%2==1 && getCell(i)==null)
				setCell(i, doors.remove(i));
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
	
	
	static void reloadCards()
	{
		
	}
}
