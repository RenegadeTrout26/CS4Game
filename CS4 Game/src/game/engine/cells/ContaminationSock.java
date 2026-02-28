package game.engine.cells;

public class ContaminationSock extends TransportCell{

	public ContaminationSock(String name, int effect) {
		super(name, effect);
		
	}

}
// Represents contamination socks that penalize monsters both in position and energy. Subclass of TransportCell and can modify energy