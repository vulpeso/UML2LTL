package pl.edu.agh.umldiagrams.exceptions;

public class GenerateLogicException extends Exception {
	private static final long serialVersionUID = 2L;

	private final String message;
	
	public GenerateLogicException(final String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
		
	}
}
