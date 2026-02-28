package game.engine.exceptions;

import java.io.IOException;

public class InvalidCSVFormat extends IOException {//6.5 
	private static final String MSG =  "Invalid input detected while reading csv file, input = \n";
	private String inputLine;
	public String getInputLine() {
		return inputLine;
	}
	public void setInputLine(String inputLine) {
		this.inputLine = inputLine;
	}
	public InvalidCSVFormat(String inputLine) {
		super(MSG); // check again
		this.inputLine = inputLine;
	}
	
	public InvalidCSVFormat(String message, String inputLine) {
		super(message);
		this.inputLine = inputLine;
	}
}
