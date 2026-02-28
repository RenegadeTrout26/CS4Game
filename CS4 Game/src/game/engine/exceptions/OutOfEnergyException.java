package game.engine.exceptions;

public class OutOfEnergyException extends GameActionException{//6.4
	private final static String MSG = "Not Enough Energy for Power Up";

	public OutOfEnergyException() {
		super(MSG);
		
	}

	public OutOfEnergyException(String message) {
		super(message);
		
	}
	
	
}
