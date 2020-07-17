package exceptions;

@SuppressWarnings("serial")
public class DeleteDebtorException extends Exception {
	
	@Override
	public String getMessage() {
		return "Delete debtor failed!";
	}
}