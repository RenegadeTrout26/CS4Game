package game.engine.cells;

import game.engine.interfaces.*;
public class ContaminationSock extends TransportCell implements CanisterModifier{

	public ContaminationSock(String name, int effect) {
		super(name, effect);
		
	}

}
// Represents contamination socks that penalize monsters both in position and energy. Subclass of TransportCell and can modify energy