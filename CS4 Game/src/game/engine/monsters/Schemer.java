package game.engine.monsters;

import game.engine.Board;
import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	public void executePowerupEffect(Monster opponentMonster) {

	    int totalStolen = 0;

	    totalStolen += stealEnergyFrom(opponentMonster);

	    for (int i = 0; i < Board.getStationedMonsters().size(); i++) {
	        totalStolen += stealEnergyFrom(Board.getStationedMonsters().get(i));
	    }

	   
	    setEnergy(getEnergy() + totalStolen + 10);
	}
	private int stealEnergyFrom(Monster target) {

	    int amount =
	        Math.min(Constants.SCHEMER_STEAL, target.getEnergy());
	   
	    if (target instanceof Schemer) {
	        target.setEnergy(target.getEnergy()-amount + 10);
	    }
	    else   if (target instanceof Dynamo) {
	        target.setEnergy(target.getEnergy()-(2*amount));
	    }
	    else   if (target instanceof MultiTasker) {
	        target.setEnergy(target.getEnergy()-amount + 200);
	    }
	    else 
	        target.setEnergy(target.getEnergy()-amount);
	    

	    return amount;
	}
}
