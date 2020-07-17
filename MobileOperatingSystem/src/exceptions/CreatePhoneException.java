package exceptions;

@SuppressWarnings("serial")
public class CreatePhoneException extends Exception {
	
	@Override
	public String getMessage() {
		return "Phone must starts with '0' and contains excatly 10 digits!\n-----------------";
	}
}