package commands.menus.admin;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.input.addClient.AddNewClientInputCommand;

public class AddNewClient implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private Command nextCommand;

	public AddNewClient(Connection connection, PrintStream printOut, BufferedReader buffReader, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		
		return getNextCommand();
	}

	private Command getNextCommand() {
		return new AddNewClientInputCommand(connection, printOut, buffReader, nextCommand);
	}
}