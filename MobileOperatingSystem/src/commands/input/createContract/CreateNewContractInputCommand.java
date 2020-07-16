package commands.input.createContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import commands.Command;
import commands.action.createContract.CreateNewContractActionCommand;

public class CreateNewContractInputCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private Command nextCommand;

	public CreateNewContractInputCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			viewClients();
			printOut.println("Choose client id");
			printOut.println("Your input please: ");
			printOut.flush();
			int clientID = Integer.parseInt(buffReader.readLine());
			
			java.sql.Date signingDate = java.sql.Date.valueOf(new java.sql.Date(new Date().getTime()).toLocalDate());
			java.sql.Date expiryDate = java.sql.Date.valueOf(new java.sql.Date(new Date().getTime()).toLocalDate().plusYears(2));
			
			printOut.println("Enter payment date");
			printOut.println("Your input please: ");
			printOut.flush();
			int paymentDate = Integer.parseInt(buffReader.readLine());
			
			printOut.println("Enter phone");
			printOut.println("Your input please: ");
			printOut.flush();
			String phone = buffReader.readLine();
			
			return new CreateNewContractActionCommand(connection, printOut, clientID, signingDate, expiryDate, paymentDate, phone, nextCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void viewClients() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
									"SELECT id, client_name " + 
									"FROM clients")
									.executeQuery();
		
		while (resultSet.next()) {
			String clientInfo = String.format("ID: %d, Name: %s ",
					resultSet.getInt("id"),
					resultSet.getString("client_name"));
			printOut.println(clientInfo);
			printOut.flush();
		}
	}
}