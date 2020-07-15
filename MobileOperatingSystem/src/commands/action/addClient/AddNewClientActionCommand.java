package commands.action.addClient;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.User;
import commands.Command;
import exceptions.AddClientException;
import exceptions.CreatePasswordException;

public class AddNewClientActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private Command nextCommand;
	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^*&+=]).{8,}";


	public AddNewClientActionCommand(Connection connection, PrintStream printOut, User user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			if(validatePass()) {
				createClient();
				printOut.println("Client was added succesfully!\n----------------");
				printOut.flush();
			}else {
				throw new CreatePasswordException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddClientException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (CreatePasswordException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	public void createClient() throws SQLException, AddClientException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO clients (client_name, client_pass) " +
								  "VALUES(?, ?)");
		ps.setString(1, user.getName());
		ps.setString(2, user.getPassword());

		if (ps.execute()) {
			throw new AddClientException();
		}
	}
	
	public boolean validatePass() throws SQLException {
		Pattern pattern1 = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher1 = pattern1.matcher(user.getPassword());

		return matcher1.matches();
	}
}