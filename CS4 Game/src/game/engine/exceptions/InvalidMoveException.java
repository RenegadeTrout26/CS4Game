package game.engine.exceptions;

public class InvalidMoveException extends GameActionException{//6.2

	private static final String MSG= "Invalid move attempted";

	public InvalidMoveException() {
		super(MSG);
		
	}

	public InvalidMoveException(String message) {
		super(message);
		
	}
	
	
}
