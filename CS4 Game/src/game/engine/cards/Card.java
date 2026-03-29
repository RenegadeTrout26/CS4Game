package game.engine.cards;

public abstract class Card { //Parent class of cards 5.13
	private final String name;
	private final String description;
	private final int rarity;
	private final boolean lucky;
	
	//constructor
	public Card(String name, String description, int rarity, boolean lucky) {
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.lucky = lucky;
	}
	
	//getters only
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getRarity() {
		return rarity;
	}
	public boolean isLucky() {
		return lucky;
	}
	
	
	
	
	
}
