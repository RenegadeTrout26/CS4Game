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
		monster.setEnergy(monster.getEnergy()+ canisterValue);
	}
	public  void onLand(Monster landingMonster, Monster opponentMonster){
		if (activated==false){ 
			ArrayList<Monster> monsters = Board.getStationedMonsters();
			if(landingMonster.getRole().equals(getRole()))
			{
			
			for(int i=0; i<monsters.size();i++){
			if (landingMonster.getRole().equals(monsters.get(i).getRole())){
				modifyCanisterEnergy(monsters.get(i),energy);
			}
			}
			modifyCanisterEnergy(landingMonster,energy);
			}
			else{
				
				if(!landingMonster.isShielded()){
				
				for(int i=0; i<monsters.size();i++){
				if (landingMonster.getRole().equals(monsters.get(i).getRole())){
					modifyCanisterEnergy(monsters.get(i),0-energy);
					
					   }  
					}
				}
				modifyCanisterEnergy(landingMonster,energy);
			}
		if (landingMonster.isShielded()){
			setActivated(true);
		}
		}
	}
}
