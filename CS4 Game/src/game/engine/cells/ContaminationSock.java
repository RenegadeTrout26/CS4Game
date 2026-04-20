package game.engine.cells;

import game.engine.Constants;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class ContaminationSock extends TransportCell implements CanisterModifier {

	public ContaminationSock(String name, int effect) {
		super(name, effect);
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.setEnergy(monster.getEnergy()- canisterValue);
		
		
	}

	public void onLand(Monster landingMonster, Monster opponentMonster) {
	modifyCanisterEnergy(landingMonster, Constants.SLIP_PENALTY);
	transport(landingMonster);
		
	}
	public void transport(Monster monster){
		super.transport(monster);
		
	}

}

