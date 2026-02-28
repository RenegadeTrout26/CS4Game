package game.engine.cells;

import game.engine.monsters.Monster;

public class MonsterCell extends Cell{//5.8
	 private Monster cellMonster;

public MonsterCell(String name, Monster cellMonster) {
	super(name);
	this.cellMonster = cellMonster;
}

public Monster getCellMonster() {
	return cellMonster;
}

}
//Represents special cells with stationed monsters. Subclass of Cell.