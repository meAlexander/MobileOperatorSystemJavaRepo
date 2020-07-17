package commands.action.viewDebtors;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;

public class ViewDebtorsActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private Command nextCommand;

	public ViewDebtorsActionCommand(Connection connection, PrintStream printOut, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			viewDebtors();
			printOut.println("---------------------");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextCommand;
	}
	
	private void viewDebtors() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT phone, client_name, contract_status, pay_date " + 
				"FROM debtors de " + 
				"JOIN contracts co ON " + 
				"de.contract_id = co.id " + 
				"JOIN clients cl ON " + 
				"cl.id = co.client_id"))
				.executeQuery();
		
		while (resultSet.next()) {
			String clientInfo = String.format("Phone: %s, Client: %s, Status: %s, Pay date: %d",
					resultSet.getString("phone"),
					resultSet.getString("client_name"),
					resultSet.getString("contract_status"),
					resultSet.getInt("pay_date"));
			printOut.println(clientInfo);
			printOut.flush();
		}
	}
}