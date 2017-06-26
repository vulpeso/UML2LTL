package pl.edu.agh.umldiagrams.exceptions;

public class ParseException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String message;
	
	public ParseException(final String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
		
	}
}
