package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
	    int stolen = stealEnergyFrom(opponentMonster);
	    
	    // Instead of two separate calls, add them together 
	    // and use the setter to force the increase.
	    int totalIncrease = stolen + 10;
	    setEnergy(getEnergy() + totalIncrease);
	}

	private int stealEnergyFrom(Monster target) {
		int amount = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());

		// reduce opponent energy
		target.alterEnergy(-amount);

		return amount;
	}
}
