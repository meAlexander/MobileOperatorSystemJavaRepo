package exceptions;

@SuppressWarnings("serial")
public class AddServiceException extends Exception {
	
	@Override
	public String getMessage() {
		return "Add service failed!\n---------------";
	}
}