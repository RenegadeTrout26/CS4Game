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

	    // steal from opponent
	    totalStolen += stealEnergyFrom(opponentMonster);

	    // steal from stationed monsters
	    for (Monster m : Board.getStationedMonsters()) {
	        totalStolen += stealEnergyFrom(m);
	    }

	    // apply energy gain once (+10 passive)
	    setEnergy(getEnergy() + totalStolen + 10);
	}
	private int stealEnergyFrom(Monster target) {

	    int amount =
	        Math.min(Constants.SCHEMER_STEAL, target.getEnergy());

	    // apply energy loss
	    target.alterEnergy(-amount);

	    // apply Schemer passive if target is Schemer
	    if (target instanceof Schemer) {
	        target.setEnergy(target.getEnergy() + 10);
	    }

	    return amount;
	}
}
