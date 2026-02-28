package game.engine.exceptions;

public abstract class GameActionException extends Exception {//6.1 Parent class of exceptions

	public GameActionException()
	{
		super();
	}
	public GameActionException(String message)
	{
		super(message);
	}
}
