package commands.input.searchClientContractByPhone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.SearchClientContractByPhone.SearchClientContractByPhoneActionCommand;

public class SearchClientContractByPhoneInputCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private Command nextCommand;

	public SearchClientContractByPhoneInputCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Enter phone number");
			printOut.println("Your input please: ");
			printOut.flush();
			
			String phone = buffReader.readLine();
			return new SearchClientContractByPhoneActionCommand(connection, printOut, phone, nextCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}