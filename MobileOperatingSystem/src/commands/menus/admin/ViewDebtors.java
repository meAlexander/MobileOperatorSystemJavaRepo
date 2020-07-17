package commands.menus.admin;

import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.checkForDebtors.CheckForDebtorsActionCommand;

public class ViewDebtors implements Command {
	private Connection connection;
	private PrintStream printOut;
	private Command nextCommand;

	public ViewDebtors(Connection connection, PrintStream printOut, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		return getNextCommand();
	}

	private Command getNextCommand() {
		//return new ViewDebtorsActionCommand(connection, printOut, nextCommand);
		return new CheckForDebtorsActionCommand(connection, printOut, nextCommand);
	}
}