package game.engine.cards;

import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Dynamo;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier {
	private final int energy;

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
	int amounToSteal = Math.min(this.energy, opponent.getEnergy());
	
	if (opponent.isShielded()){
		modifyCanisterEnergy(opponent, -amounToSteal);
	}else{
		modifyCanisterEnergy(opponent, -amounToSteal);
		modifyCanisterEnergy(player, amounToSteal);
	}

	}
	

//public static void main(String[] args ){
//	Monster x = new Dynamo("hamada"," idk", Role.LAUGHER, 20);
//	Monster y = new Dynamo("hamada2"," idk", Role.SCARER, 50);
//	y.setShielded(false);
//	EnergyStealCard e = new EnergyStealCard("card"," description", 5, 45);
//	e.performAction(x, y);
//	System.out.println(y.getEnergy());
//	System.out.println(x.getEnergy());
//	System.out.println(y.isShielded());
//}	
	
	
}
