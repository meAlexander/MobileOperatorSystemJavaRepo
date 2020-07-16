package exceptions;

@SuppressWarnings("serial")
public class PhoneException extends Exception {
	
	@Override
	public String getMessage() {
		return "Client with this phone number does not exist!\n------------------";
	}
}