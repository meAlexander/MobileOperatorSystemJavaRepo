package exceptions;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	
	@Override
	public String getMessage() {
		return "Service with this id does not exist!\n------------------";
	}
}