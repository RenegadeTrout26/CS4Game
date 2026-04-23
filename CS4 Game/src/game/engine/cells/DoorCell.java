package game.engine.cells;

import game.engine.Board;
import java.util.ArrayList;
import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class DoorCell extends Cell implements CanisterModifier {
	private Role role;
	private int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}
	
	public Role getRole() {
		return role;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		if (monster.getRole().equals(role)) {
			monster.alterEnergy(canisterValue);
		} else {
			monster.alterEnergy(-canisterValue);
		}
	}
	
	
	
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		if (activated) {
			return;
		}

		ArrayList<Monster> stationedMonsters = Board.getStationedMonsters();
		
		if (!landingMonster.getRole().equals(getRole()) && landingMonster.isShielded()) {
			landingMonster.alterEnergy(-energy);
			return;
		}

		modifyCanisterEnergy(landingMonster, energy);
		for (int i=0; i<stationedMonsters.size();i++) {
			if (stationedMonsters.get(i).getRole().equals(landingMonster.getRole())) {
				modifyCanisterEnergy(stationedMonsters.get(i), energy);
			}
		}

		setActivated(true);
	}
}

