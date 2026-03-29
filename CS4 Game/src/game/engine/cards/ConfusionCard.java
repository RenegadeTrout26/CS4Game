package game.engine.cards;

public class ConfusionCard extends Card{ 
	

	//5.17
   //Represents confusion cards that confuse monsters of their role. Subclass of Card
private final int  duration;

//getter
public int getDuration() {
	return duration;
}

//constructor
public ConfusionCard(String name, String description, int rarity,int duration) {
	super(name, description, rarity, false);
	this.duration=duration;
}





}



