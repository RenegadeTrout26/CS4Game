package game.engine.cells;

import game.engine.monsters.*;

public class MonsterCell extends Cell {
	private Monster cellMonster;

	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}

	public Monster getCellMonster() {
		return cellMonster;
	}

 public void onLand(Monster landingMonster, Monster opponentMonster){
		 if(cellMonster.getRole().equals(landingMonster.getRole())){
			 landingMonster.executePowerupEffect(opponentMonster);
		 }
		 else {
			 if (landingMonster.getEnergy()> cellMonster.getEnergy()){
				 //cell monster fe temp, hasnwadi landig fe cell, law shielded nthng, law la2 hghana5od temp fe lanf=ding
				int temp = cellMonster.getEnergy();
				 cellMonster.setEnergy(landingMonster.getEnergy());
				 if (!landingMonster.isShielded()){
					 landingMonster.setEnergy(temp);
				 }
				 if (landingMonster.isShielded()){
					 landingMonster.setShielded(false);
				 }
			 }
		 }
	 }
}
