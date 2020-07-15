package commands.input.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.action.login.LoginAdminActionCommand;

public class GetAdminInputLoginCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetAdminInputLoginCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter your Admin Name");
			printOut.println("Your input please: ");
			printOut.flush();
			String adminName = buffReader.readLine();
			
			printOut.println("Please enter your password");
			printOut.println("Your input please: ");
			printOut.flush();
			String password = buffReader.readLine();

			User user = new User(adminName, password);
			return new LoginAdminActionCommand(connection, printOut, buffReader, user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}