package commands.action.login;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.UserLogin;
import commands.Command;
import commands.menus.LoginMenuCommand;
import commands.menus.user.LoggedInUserMenuCommand;
import exceptions.LoginException;

public class LoginUserActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private UserLogin user;

	public LoginUserActionCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, UserLogin user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		try {
			if (checkUserInfo()) {
				return new LoggedInUserMenuCommand(connection, printOut, buffReader, user);
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

	public boolean checkUserInfo() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(String.format(
				"SELECT phone, client_pass " +
				"FROM clients cl " +
				"JOIN contracts co ON " +
				"cl.id = co.client_id " +
				"WHERE phone LIKE '%s' " +
				"AND client_pass COLLATE utf8mb4_0900_as_cs LIKE '%s'",
				user.getPhone(), user.getPassword())).executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}
}