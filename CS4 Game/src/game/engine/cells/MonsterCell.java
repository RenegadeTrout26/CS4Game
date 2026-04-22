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
		super.onLand(landingMonster,opponentMonster);	 
		
		
		if (cellMonster.getRole().equals(landingMonster.getRole())) {
			landingMonster.executePowerupEffect(opponentMonster);
		}
		
			 int cellMonsterEnergy = cellMonster.getEnergy();
				int landingMonsterEnergy = landingMonster.getEnergy();
				
				if (landingMonsterEnergy > cellMonsterEnergy) {	
					cellMonster.alterEnergy(landingMonsterEnergy - cellMonsterEnergy);
					landingMonster.alterEnergy(cellMonsterEnergy - landingMonsterEnergy);
		 }
	 }
}
