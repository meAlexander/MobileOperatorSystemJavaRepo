package commands.action.createContract;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commands.Command;
import exceptions.AddContractException;
import exceptions.ClientException;
import exceptions.CreatePhoneException;

public class CreateNewContractActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private int clientID;
	private java.sql.Date signingDate;
	private java.sql.Date expiryDate;
	private int paymentDate;
	private String phone;
	private Command nextCommand;
	private static final String PHONE_PATTERN = "^[0]+[0-9]{9}";

	public CreateNewContractActionCommand(Connection connection, PrintStream printOut, int clientID,
			java.sql.Date signingDate, java.sql.Date expiryDate, int paymentDate, String phone, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.clientID = clientID;
		this.signingDate = signingDate;
		this.expiryDate = expiryDate;
		this.paymentDate = paymentDate;
		this.phone = phone;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			checkClient();
			if(validatePhone()) {
				createContract();
				printOut.println("The contract was created successfully!\n---------------");
				printOut.flush();
			}else {
				throw new CreatePhoneException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddContractException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (ClientException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (CreatePhoneException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	private void createContract() throws SQLException, AddContractException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO contracts (signing_date, expiry_date, payment_date, phone, client_id) "
						+ "VALUES(?, ?, ?, ?, ?)");
		ps.setDate(1, signingDate);
		ps.setDate(2, expiryDate);
		ps.setInt(3, paymentDate);
		ps.setString(4, phone);
		ps.setInt(5, clientID);

		if (ps.execute()) {
			throw new AddContractException();
		}
	}
	
	private void checkClient() throws SQLException, ClientException {
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT id " + 
				"FROM clients " +
				"WHERE id = %d", clientID))
				.executeQuery();

		if (resultSet.next()) {
			return;
		}else {
			throw new ClientException();
		}
	}
	
	private boolean validatePhone() {
		Pattern pattern = Pattern.compile(PHONE_PATTERN);
	    Matcher matcher = pattern.matcher(phone);
	    
	    return matcher.matches();
	}
}