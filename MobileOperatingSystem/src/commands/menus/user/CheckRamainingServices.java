package commands.menus.user;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.UserLogin;
import commands.Command;

public class CheckRamainingServices implements Command {
	private Connection connection;
	private PrintStream printOut;
	private UserLogin user;
	private Command nextCommand;

	public CheckRamainingServices(Connection connection, PrintStream printOut, UserLogin user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			getRemainingServices();
			printOut.println("------------");;
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextCommand;
	}
	
	private void getRemainingServices() throws SQLException {
		String remainServices = null;
		ResultSet resultSet = connection.prepareStatement(
						String.format(
								"SELECT service_name, consumption " + 
								"FROM contracts_services cs " + 
								"JOIN services s ON " + 
								"s.id = cs.service_id " +
								"JOIN contracts c ON " +
								"c.id = cs.contract_id " +
								"WHERE phone = '%s'", user.getPhone()))
								.executeQuery();
		
		while (resultSet.next()) {
			remainServices = String.format("Service: %s Consumption: %.2f",
					resultSet.getString("service_name"),
					resultSet.getDouble("consumption"));
			printOut.println(remainServices);
			printOut.flush();
		}
	}
}