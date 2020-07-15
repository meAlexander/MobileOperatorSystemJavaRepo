package exceptions;

@SuppressWarnings("serial")
public class CreatePasswordException extends Exception{
	
	@Override
	public String getMessage() {
		return "Your password must contain a minimum of 8 characters, uppercase and lowercase letters, a number and special symbol!";
	}
}