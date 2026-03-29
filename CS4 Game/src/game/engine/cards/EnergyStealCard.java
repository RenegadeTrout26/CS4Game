package game.engine.cards;
import game.engine.interfaces.*;
public class EnergyStealCard extends Card implements CanisterModifier{ 
	//5.15
	//Represents energy steal cards. Subclass of Card and can modify energy.
		
    private final int energy;
	
	
	//getter
	public int getEnergy() {
		return energy;
	}


	//constructor
	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description, rarity, true);
		this.energy=energy;
		}
	
}
