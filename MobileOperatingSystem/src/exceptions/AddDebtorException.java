package exceptions;

@SuppressWarnings("serial")
public class AddDebtorException extends Exception {
	
	@Override
	public String getMessage() {
		return "Add debtor failed!";
	}
}