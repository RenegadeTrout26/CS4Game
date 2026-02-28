package game.engine.cards;

public class EnergyStealCard extends Card{ 
	//5.15
	//Represents energy steal cards. Subclass of Card and can modify energy.
		
    private int energy;
	
	
	
	public int getEnergy() {
		return energy;
	}



	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description, rarity, true);
		this.energy=energy;
		}
	
}
