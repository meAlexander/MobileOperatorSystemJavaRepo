package commands.menus.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.menus.MainMenuCommand;
import exceptions.InputOptionException;

public class LoggedInAdminMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public LoggedInAdminMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Login admin menu:\n"
					+ "1.Add services\n"
					+ "2.Add new client\n"
					+ "3.Search client contract\n"
					+ "4.Create new contract\n"
					+ "5.View debtors\n"
					+ "6.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();

			String adminMenuAnswer = buffReader.readLine();
			return getNextCommand(adminMenuAnswer, parent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.println(e.getMessage());
			printOut.flush();
			return parent;
//			return new LoggedInAdminMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String adminMenuAnswer, Command nextCommand) throws InputOptionException {
		switch (adminMenuAnswer) {
		case "Add services":
		case "1":
			return new AddServices(connection, printOut, buffReader);
		case "Add new client":
		case "2":
			return new AddNewClient(connection, printOut, buffReader, nextCommand);
		case "Search client contract":
		case "3":
			return new SearchClientContract(connection, printOut, buffReader, nextCommand);
		case "Create new contract":
		case "4":
			return new CreateNewContract(connection, printOut, buffReader, nextCommand);
		case "View debtors":
		case "5":
			return new ViewDebtors(connection, printOut, buffReader);
		case "Main menu":
		case "6":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}