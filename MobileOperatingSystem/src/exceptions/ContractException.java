package exceptions;

@SuppressWarnings("serial")
public class ContractException extends Exception {
	
	@Override
	public String getMessage() {
		return "Contract with this id does not exist!\n-------------------";
	}
}