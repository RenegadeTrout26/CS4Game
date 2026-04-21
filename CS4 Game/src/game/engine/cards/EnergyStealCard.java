package game.engine.cards;

import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Dynamo;
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
		monster.alterEnergy(canisterValue);
		}
	

	@Override
	public void performAction(Monster player, Monster opponent) {
	if(!(opponent.isShielded())){
				if(this.getEnergy()<opponent.getEnergy()){
					modifyCanisterEnergy(opponent,-this.getEnergy());
				    modifyCanisterEnergy(player,this.getEnergy());
				
				}
				else{
					int c =opponent.getEnergy();
					modifyCanisterEnergy(opponent,-opponent.getEnergy());
				modifyCanisterEnergy(player,c);
      
				}
		}else{
		opponent.setShielded(false);
	}

	}
	
/*
public static void main(String[] args ){
	Monster x = new Dynamo("hamada"," idk", Role.LAUGHER, 20);
	Monster y = new Dynamo("hamada2"," idk", Role.SCARER, 50);
	y.setShielded(true);
	EnergyStealCard e = new EnergyStealCard("card"," description", 5, 45);
	e.performAction(x, y);
	System.out.println(y.getEnergy());
	System.out.println(x.getEnergy());
	System.out.println(y.isShielded());
}	*/
	
	
}
