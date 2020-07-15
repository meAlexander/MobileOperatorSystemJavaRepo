package exceptions;

@SuppressWarnings("serial")
public class LoginException extends Exception{
	
	@Override
	public String getMessage() {
		return "Wrong username or password! Try again!";
	}
}