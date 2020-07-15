package client;

public class UserLogin extends User{
	private String phone;
	
	public UserLogin(String name, String password, String phone) {
		super(name, password);
		this.phone = phone;
	}
	
	public UserLogin(String password, String phone) {
		this("NULL", password, phone);
	}
	
	public String getPhone() {
		return phone;
	}
}