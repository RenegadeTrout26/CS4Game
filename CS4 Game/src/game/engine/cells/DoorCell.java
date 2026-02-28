package game.engine.cells;

import game.engine.Role;

public class DoorCell extends Cell{
	 Role role;
	 int energy;
	 boolean activated;
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}
	public Role getRole(){
		return role;
		
	}
	
	public int getEnergy(){
		return energy;
		
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	}
//Represents doors where monsters collect or lose energy depending on role. Subclass of Cell and can modify canister energy.
