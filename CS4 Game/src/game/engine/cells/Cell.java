package game.engine.cells;

import game.engine.monsters.Monster;
//A class representing the cells on the game board that a monster can land on. This class can be instantiated as normal cells with no special features.
public class Cell {//5.6 Parent class of all cells
	private String name;
	private Monster monster;
	 public Cell(String name){
		 this.name=name;
		 this.monster=null;
	 }
	 public String getName() {
	        return name;
	    }
	public Monster getMonster() {
		return monster;
	}
	public void setMonster(Monster monster) {
		this.monster = monster;
	}
}
//subclasses: DoorCell,TransportCell,MonsterCell, CardCell
