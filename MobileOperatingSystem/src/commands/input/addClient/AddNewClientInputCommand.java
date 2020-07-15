package commands.input.addClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.action.addClient.AddNewClientActionCommand;

public class AddNewClientInputCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private Command nextCommand;

	public AddNewClientInputCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Enter client name");
			printOut.println("Your input please: ");
			printOut.flush();
			String name = buffReader.readLine();
			
			printOut.println("Enter client password");
			printOut.println("Your input please: ");
			printOut.flush();
			String password = buffReader.readLine();
			
			User user = new User(name, password);
			return new AddNewClientActionCommand(connection, printOut, user, nextCommand);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}