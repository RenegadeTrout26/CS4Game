package game.engine;

import game.engine.cells.*;
import game.engine.monsters.*;
import game.engine.cards.*;
import java.util.ArrayList;


public class Board { //5.21
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters;
	private static ArrayList<Card> originalCards;
	private static ArrayList<Card> cards;
	public  ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	public void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}
	public Cell[][] getBoardCells() {
		return boardCells;
	}
	public ArrayList<Card> getOriginalCards() {
		return originalCards;
	}
	
	public Board(ArrayList<Card> readCards)
	{
		boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<>();
		cards = new ArrayList<>();
		originalCards
		
	}
	
}
