package commands.menus.user;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;

public class ViewServices implements Command {
	private static Connection connection;
	private static PrintStream printOut;
	private Command nextCommand;

	public ViewServices(Connection connection, PrintStream printOut, Command nextCommand) {
		ViewServices.connection = connection;
		ViewServices.printOut = printOut;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			getAllServices();
			printOut.println("------------");;
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextCommand;
	}
	
	public static void getAllServices() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				"SELECT service_name, service_limit, bill " +
				"FROM services " )
				.executeQuery();
		
		while (resultSet.next()) {
			String availableService = String.format("Service: %s, Limit: %.2f, Bill: %.2f",
					resultSet.getString("service_name"),
					resultSet.getDouble("service_limit"),
					resultSet.getDouble("bill"));
			printOut.println(availableService);
			printOut.flush();
		}
	}
}