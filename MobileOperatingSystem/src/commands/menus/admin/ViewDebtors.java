package commands.menus.admin;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;

public class ViewDebtors implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public ViewDebtors(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}
	
	@Override
	public Command execute(Command parent) {
		
		return null;
	}
}