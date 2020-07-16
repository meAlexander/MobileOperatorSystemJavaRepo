package commands.action.addServiceToClientContract;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.AddServiceException;
import exceptions.ContractException;
import exceptions.ServiceException;

public class AddServiceToClientContractActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private int contractID;
	private int serviceID;
	private Command nextCommand;

	public AddServiceToClientContractActionCommand(Connection connection, PrintStream printOut, int contractID, int serviceID, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.contractID = contractID;
		this.serviceID = serviceID;
		this.nextCommand = nextCommand;
	}
	@Override
	public Command execute(Command parent) {
		try {
			addServiceToClient();
			
			printOut.println("The service was added!\n--------------");;
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddServiceException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (ServiceException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (ContractException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}
	
	private void addServiceToClient() throws SQLException, AddServiceException, ServiceException, ContractException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO contracts_services (contract_id, service_id, consumption) " + 
				"VALUES(?, ?, ?)");
		ps.setInt(1, contractID);
		ps.setInt(2, serviceID);
		ps.setDouble(3, getConsumption());

		if (ps.execute()) {
			throw new AddServiceException();
		}
	}
	
	private double getConsumption() throws SQLException, ServiceException, ContractException {
		double consumption = 0;
		ResultSet resultSet = connection.prepareStatement(
									String.format(
									"SELECT service_limit " + 
									"FROM services " +
									"WHERE id = %d", serviceID))
									.executeQuery();
		
		if (resultSet.next()) {
			consumption = resultSet.getDouble("service_limit");
		}else {
			throw new ServiceException();
		}
		checkContract();
		return consumption;
	}
	
	private void checkContract() throws SQLException, ContractException{
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT id " + 
				"FROM contracts " +
				"WHERE id = %d", contractID))
				.executeQuery();

		if (resultSet.next()) {
			return;
		}else {
			throw new ContractException();
		}
	}
}