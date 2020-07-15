package exceptions;

@SuppressWarnings("serial")
public class InputOptionException extends Exception {
	
	@Override
	public String getMessage() {
		return "Invalid option!";
	}
}