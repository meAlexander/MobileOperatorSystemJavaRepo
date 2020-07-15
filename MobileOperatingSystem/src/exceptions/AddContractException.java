package exceptions;

@SuppressWarnings("serial")
public class AddContractException extends Exception {
	
	@Override
	public String getMessage() {
		return "Add contract failed!";
	}
}