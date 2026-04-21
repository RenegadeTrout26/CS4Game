package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier {
	private int energy;

	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description, rarity, true);
		this.energy = energy;
	}
	
	public int getEnergy() {
		return energy;
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.setEnergy(monster.getEnergy()+ canisterValue);	
		if (canisterValue<0){
			monster.setShielded(false);
		}
	}

	@Override
	public void performAction(Monster player, Monster opponent) {
		if(!(opponent.isShielded())){
				if(this.getEnergy()<opponent.getEnergy()){
					modifyCanisterEnergy(opponent,0-this.getEnergy());
				    modifyCanisterEnergy(player,this.getEnergy());
				//	opponent.setEnergy(opponent.getEnergy()-energy);
				//	player.setEnergy(player.getEnergy()+energy);
				}
				else{
					modifyCanisterEnergy(opponent,0-opponent.getEnergy());
				modifyCanisterEnergy(player,opponent.getEnergy());
         // opponent.setEnergy(0);
        // player.setEnergy(player.getEnergy()+opponent.getEnergy());
					
				}
		}else{
			opponent.setShielded(false);
		}

	}
	
}
