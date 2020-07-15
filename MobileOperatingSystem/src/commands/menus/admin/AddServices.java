package commands.menus.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.input.addNewService.AddNewServiceInputCommand;
import commands.input.addServiceToClientContract.AddServiceToClientCotractInputCommand;
import exceptions.InputOptionException;

public class AddServices implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public AddServices(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("1.Add service to client 2.Add new service 3.Admin menu");
			printOut.println("Your input please: ");
			printOut.flush();

			String searchClientAnswer = buffReader.readLine();
			return getNextCommand(searchClientAnswer, parent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return null;	
	}

	private Command getNextCommand(String searchClientAnswer, Command parent) throws InputOptionException {
		switch (searchClientAnswer) {
		case "Add service to client":
		case "1":
			return new AddServiceToClientCotractInputCommand(connection, printOut, buffReader, parent);
		case "Add new service":
		case "2":
			return new AddNewServiceInputCommand(connection, printOut, buffReader, parent);
		case "Admin menu":
		case "3":
			return new LoggedInAdminMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}