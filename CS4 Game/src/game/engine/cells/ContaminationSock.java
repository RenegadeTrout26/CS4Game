package game.engine.cells;

import game.engine.Constants;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Dynamo;
import game.engine.monsters.Monster;
import game.engine.monsters.MultiTasker;
import game.engine.monsters.Schemer;

public class ContaminationSock extends TransportCell implements CanisterModifier {

	public ContaminationSock(String name, int effect) {
		super(name, effect);
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {

			if (canisterValue < 0 && monster.isShielded()) {
				monster.setShielded(false);
				return;
			}
			int newCanisterValue = canisterValue;
			if (canisterValue != 0) {
				if (monster instanceof Dynamo) {
					newCanisterValue = newCanisterValue * 2;
				} else if (monster instanceof MultiTasker) {
					newCanisterValue = newCanisterValue + Constants.MULTITASKER_BONUS;
				} else if (monster instanceof Schemer) {
					newCanisterValue = newCanisterValue + Constants.SCHEMER_STEAL;
				}
			}
			monster.setEnergy(monster.getEnergy() + newCanisterValue);

	}

	
	public void transport(Monster monster){
        super.transport(monster);		
		

			modifyCanisterEnergy(monster, -Constants.SLIP_PENALTY);
			
  }
}

