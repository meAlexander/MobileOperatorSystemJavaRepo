package commands.action.createContract;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.AddContractException;

public class CreateNewContractActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private int clientID;
	private java.sql.Date signingDate;
	private java.sql.Date expiryDate;
	private int paymentDate;
	private String phone;
	private Command nextCommand;

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
			createContract();
			printOut.println("The contract was created successfully!\n---------------");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddContractException e) {
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
}