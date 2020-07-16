package commands.action.addNewService;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.AddServiceException;

public class AddNewServiceActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private String service;
	private double limit;
	private double bill;
	private Command nextCommand;

	public AddNewServiceActionCommand(Connection connection, PrintStream printOut, String service, double limit, double bill, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.service = service;
		this.limit = limit;
		this.bill = bill;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			addService();
			printOut.println("The new service was added!\n--------------");;
			printOut.flush();
		} catch (SQLException e) {
			if (!(service.equals("MIN")) || !(service.equals("MB")) || !(service.equals("SMS"))) {
				printOut.println("Service must be 'MIN', 'MB', 'SMS'\n-----------------");
				printOut.flush();
			}
		} catch (AddServiceException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}
	
	private void addService() throws SQLException, AddServiceException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO services(service_name, service_limit, bill) " +
						          "VALUES(?, ?, ?)");
		ps.setString(1, service);
		ps.setDouble(2, limit);
		ps.setDouble(3, bill);

		if (ps.execute()) {
			throw new AddServiceException();
		}
	}
}