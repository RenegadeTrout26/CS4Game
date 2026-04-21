package game.engine.cards;

import game.engine.Role;
import game.engine.monsters.Dynamo;
import game.engine.monsters.Monster;

public class ConfusionCard extends Card {
	private int duration;
	
	public ConfusionCard(String name, String description, int rarity, int duration) {
		super(name, description, rarity, false);
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}

	@Override
	public void performAction(Monster player, Monster opponent) {
		player.setConfusionTurns(getDuration());
		opponent.setConfusionTurns(getDuration());
Role temp = player.getRole();
player.setRole(opponent.getRole());
opponent.setRole(temp);
	}
	/*public static void main(String[] args ){
		Monster x = new Dynamo("hamada"," idk", Role.LAUGHER, 20);
		Monster y = new Dynamo("hamada2"," idk", Role.SCARER, 50);
		y.setShielded(true);
		ConfusionCard e = new ConfusionCard("card"," description", 5, 5);
		e.performAction(x, y);
		System.out.println(x.getRole());
		System.out.println(y.getRole());
		System.out.println(x.getConfusionTurns());
		System.out.println(y.getConfusionTurns());

	}*/
}
