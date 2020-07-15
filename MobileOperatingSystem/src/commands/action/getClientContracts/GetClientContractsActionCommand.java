package commands.action.getClientContracts;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;

public class GetClientContractsActionCommand implements Command{
	private static Connection connection;
	private static PrintStream printOut;
	private Command nextCommand;
	
	public GetClientContractsActionCommand(Connection connection, PrintStream printOut, Command nextCommand) {
		GetClientContractsActionCommand.connection = connection;
		GetClientContractsActionCommand.printOut = printOut;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			getAllContractsInfo();
			printOut.println("--------------");
			printOut.flush();
			
			return nextCommand;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void getAllContractsInfo() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT signing_date, expiry_date, payment_date, phone, client_name " + 
				"FROM contracts co " + 
				"JOIN clients cl ON " + 
				"co.client_id = cl.id"))
				.executeQuery();
		
		while (resultSet.next()) {
			String clientInfo = String.format("Signing date: %s, Expiry date: %s, Payment date: %d,\nPhone: %s, Client: %s",
					resultSet.getDate("signing_date").toString(),
					resultSet.getDate("expiry_date").toString(),
					resultSet.getInt("payment_date"),
					resultSet.getString("phone"),
					resultSet.getString("client_name"));
			printOut.println(clientInfo);
			printOut.flush();
		}
	}
}