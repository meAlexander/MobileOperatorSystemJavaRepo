package exceptions;

@SuppressWarnings("serial")
public class ClientException extends Exception {
	
	@Override
	public String getMessage() {
		return "Client with this id does not exist!\n-------------------";
	}
}