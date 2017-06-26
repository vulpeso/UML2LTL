package pl.edu.agh.umldiagrams.exceptions;

public class ConfigException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String message;
	
	public ConfigException(final String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
		
	}
}
