package exceptions;

@SuppressWarnings("serial")
public class PayBillException extends Exception {
	
	@Override
	public String getMessage() {
		return "Pay a bill failed!";
	}
}