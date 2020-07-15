package commands.input.addServiceToClientContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import commands.action.addServiceToClientContract.AddServiceToClientContractActionCommand;

public class AddServiceToClientCotractInputCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private Command nextCommand;

	public AddServiceToClientCotractInputCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			viewContracts(parent);
			printOut.println("Choose contract id");
			printOut.println("Your input please: ");
			printOut.flush();
			int contractID = Integer.parseInt(buffReader.readLine());
			
			viewServices();
			printOut.println("Choose service id");
			printOut.println("Your input please: ");
			printOut.flush();
			int serviceID = Integer.parseInt(buffReader.readLine());
			
			return new AddServiceToClientContractActionCommand(connection, printOut, contractID, serviceID, nextCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			printOut.println("Input has to be a number.");
			printOut.flush();
			return nextCommand;
		}
		return null;
	}
	
	private void viewServices() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
									"SELECT id, service_name, service_limit, bill " + 
									"FROM services")
									.executeQuery();
		
		while (resultSet.next()) {
			String clientInfo = String.format("ID: %d, Service: %s, Limit: %.2f, Bill: %.2f",
					resultSet.getInt("id"),
					resultSet.getString("service_name"),
					resultSet.getDouble("service_limit"),
					resultSet.getDouble("bill"));
			printOut.println(clientInfo);
			printOut.flush();
		}
	}
	
	private void viewContracts(Command parent) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				"SELECT co.id, signing_date, expiry_date, payment_date, phone, client_name " +
				"FROM contracts co " +
				"JOIN clients cl ON " +
				"co.client_id = cl.id" )
				.executeQuery();
		
		while (resultSet.next()) {
			String availableService = String.format("ID: %d, Signing date: '%s', Expiry date: '%s', Payment date: %d, Phone: '%s', Client: '%s'",
					resultSet.getInt("id"),
					resultSet.getDate("signing_date"),
					resultSet.getDate("expiry_date"),
					resultSet.getInt("payment_date"),
					resultSet.getString("phone"),
					resultSet.getString("client_name"));
			printOut.println(availableService);
			printOut.flush();
		}
	}
}