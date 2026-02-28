package game.engine.cells;

public abstract class TransportCell extends Cell{//5.10 Parent of conveyor and sock
	int effect;

public TransportCell(String name, int effect) {
	super(name);
	this.effect = effect;
}

public int getEffect() {
	return effect;
}

}
//subclasses: ConveyorBelt,ContaminationSock
// Represent cells that transport monsters. Subclass of Cell and No objects of type TransportCell can be instantiated