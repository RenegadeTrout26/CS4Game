package game.engine.monsters;

import game.engine.Board;
import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
	    int totalStolen = 0;

	    // 1. REMOVE the separate steal from opponentMonster on line 19.
	    
	    // 2. The Loop
	    for (Monster m : Board.getStationedMonsters()) {
	      
	        if (m != this) {
	            totalStolen += stealEnergyFrom(m);
	        }
	    }

	    // 3. Final update
	    this.setEnergy(this.getEnergy() + totalStolen + 10);
	}

	private int stealEnergyFrom(Monster target) {
		int amount = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());

		// reduce opponent energy
		target.alterEnergy(amount);

		return amount;
	}
}
