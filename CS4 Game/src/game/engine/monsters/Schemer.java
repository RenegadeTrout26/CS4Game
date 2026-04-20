package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		// TODO Auto-generated method stub
		 int totalStolen = 0;

	        // steal from opponent
	        totalStolen += stealEnergyFrom(opponentMonster);

	        // NOTE: stationed monsters loop depends on your engine structure
	        // Example (if you have a list/array somewhere):
	        //
	        // for (Monster m : stationedMonsters) {
	        //     totalStolen += stealEnergyFrom(m);
	        // }

	        // apply total once at end
	        this.alterEnergy(totalStolen);
		
		
	}
	private int stealEnergyFrom(Monster target) {
	    int stealAmount = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());
	    
	    // Reduce target's energy (respecting shield logic)
	    target.alterEnergy(-stealAmount);
	    
	    return stealAmount;
	}
	 public void alterEnergy(int energy) {
	        super.alterEnergy(energy + 10);
	    }
	 
}
