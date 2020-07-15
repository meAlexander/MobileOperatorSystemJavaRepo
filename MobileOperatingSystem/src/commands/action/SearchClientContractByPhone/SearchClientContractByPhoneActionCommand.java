package commands.action.SearchClientContractByPhone;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;

public class SearchClientContractByPhoneActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private String phone;
	private Command nextCommand;

	public SearchClientContractByPhoneActionCommand(Connection connection, PrintStream printOut, String phone, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.phone = phone;
		this.nextCommand = nextCommand;
	}
	@Override
	public Command execute(Command parent) {
		try {
			getClientContractByPhone();
			printOut.println("----------------");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextCommand;
	}
	
	private void getClientContractByPhone() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT signing_date, expiry_date, payment_date, phone, client_name " + 
				"FROM contracts co " + 
				"JOIN clients cl ON " + 
				"co.client_id = cl.id " + 
				"WHERE phone LIKE '%s'", phone))
				.executeQuery();
		
		if (resultSet.next()) {
			String clientInfo = String.format("Signing date: %s, Expiry date: %s, Payment date: %d,\nPhone: %s, Client: %s",
					resultSet.getDate("signing_date").toString(),
					resultSet.getDate("expiry_date").toString(),
					resultSet.getInt("payment_date"),
					resultSet.getString("phone"),
					resultSet.getString("client_name"));
			printOut.println(clientInfo);
		}
	}
}