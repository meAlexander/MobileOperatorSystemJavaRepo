package commands.menus.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.input.searchClientContractByPhone.SearchClientContractByPhoneInputCommand;
import exceptions.InputOptionException;

public class SearchClientContract implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private Command nextCommand;

	public SearchClientContract(Connection connection, PrintStream printOut, BufferedReader buffReader, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Search client contract by: 1.Phone number 2.Admin menu");
			printOut.println("Your input please: ");
			printOut.flush();

			String searchClientAnswer = buffReader.readLine();
			return getNextCommand(searchClientAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.println(e.getMessage());
			printOut.flush();
			return parent;
		}
		return null;
	}

	private Command getNextCommand(String searchClientAnswer) throws InputOptionException {
		switch (searchClientAnswer) {
		case "Phone number":
		case "1":
			return new SearchClientContractByPhoneInputCommand(connection, printOut, buffReader, nextCommand);
		case "Admin menu":
		case "2":
			return new LoggedInAdminMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}