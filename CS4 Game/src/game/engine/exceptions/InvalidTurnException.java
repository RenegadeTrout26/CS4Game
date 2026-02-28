package game.engine.exceptions;

public class InvalidTurnException extends GameActionException {//6.3
	private static final String MSG = "Action done on wrong turn";

	public InvalidTurnException() {
		super(MSG);
		
	}

	public InvalidTurnException(String message) {
		super(message);
		
	}
	
	
}
