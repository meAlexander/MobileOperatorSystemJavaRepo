package commands.input.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.UserLogin;
import commands.Command;
import commands.action.login.LoginUserActionCommand;

public class GetUserInputLoginCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetUserInputLoginCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter your phone number");
			printOut.println("Your input please: ");
			printOut.flush();
			String userPhone = buffReader.readLine();

			printOut.println("Please enter your password");
			printOut.println("Your input please: ");
			printOut.flush();
			String password = buffReader.readLine();

			UserLogin userlogin = new UserLogin(password, userPhone);
			return new LoginUserActionCommand(connection, printOut, buffReader, userlogin);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}