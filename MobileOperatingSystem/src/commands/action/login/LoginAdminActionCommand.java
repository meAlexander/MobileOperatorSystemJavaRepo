package commands.action.login;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import commands.menus.LoginMenuCommand;
import commands.menus.admin.LoggedInAdminMenuCommand;
import exceptions.LoginException;

public class LoginAdminActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public LoginAdminActionCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		try {
			if (checkAdminInfo()) {
				return new LoggedInAdminMenuCommand(connection, printOut, buffReader);
			} else {
				throw new LoginException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginException e) {
			printOut.println(e.getMessage());
			printOut.flush();
			return new LoginMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	public boolean checkAdminInfo() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(String.format(
				"SELECT admin_name, admin_pass " +
				"FROM admins " +
				"WHERE admin_name COLLATE utf8mb4_0900_as_cs LIKE '%s' " +
				"AND admin_pass COLLATE utf8mb4_0900_as_cs LIKE '%s'",
				user.getName(), user.getPassword())).executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}
}