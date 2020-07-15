package commands.menus.user;

import java.io.PrintStream;
import java.sql.Connection;

import client.UserLogin;
import commands.Command;
import commands.action.viewNotification.ViewNotificationActionCommand;

public class ViewNotifications implements Command {
	private Connection connection;
	private PrintStream printOut;
	private UserLogin user;
	private Command nextCommand;

	public ViewNotifications(Connection connection, PrintStream printOut, UserLogin user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		
		return getNextCommand();
	}

	public Command getNextCommand() {
		
		return new ViewNotificationActionCommand(connection, printOut, user, nextCommand);
	}
}