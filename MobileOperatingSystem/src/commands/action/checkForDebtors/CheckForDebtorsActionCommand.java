package commands.action.checkForDebtors;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import commands.Command;
import exceptions.AddDebtorException;
import exceptions.DeleteDebtorException;

public class CheckForDebtorsActionCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	
	public CheckForDebtorsActionCommand(Connection connection, PrintStream printOut) {
		this.connection = connection;
		this.printOut = printOut;
	}

	@Override
	public Command execute(Command parent) {
		try {
			checkForDebtors();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddDebtorException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (DeleteDebtorException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return null;
	}

	private void checkForDebtors() throws SQLException, AddDebtorException, DeleteDebtorException {
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int day = localDate.getDayOfMonth();
		
		ResultSet resultSet = connection.prepareStatement(
				"SELECT id, payment_date " + 
				"FROM contracts")
				.executeQuery();
		
		while (resultSet.next()) {
			int clientInfo = resultSet.getInt("payment_date");
			int contractID = (Integer)resultSet.getInt("id");
			
			if(checkContract(contractID).equals("no")) {
				addDebtor(contractID);
			}
			
			if (checkContract(contractID).equals("paid") && checkPayDate(contractID) != day) {
				if(day == clientInfo) {
					delete(contractID);
					addDebtor(contractID);
				}
			}
		}
	}
	
	private int checkPayDate(int contractID) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT pay_date " + 
				"FROM debtors " + 
				"WHERE contract_id = %d ", contractID))
				.executeQuery();
		if(resultSet.next()) {
			return resultSet.getInt("pay_date");
		}
		return 0;
	}

	private String checkContract(int contractID) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT contract_id, contract_status " + 
				"FROM debtors " + 
				"WHERE contract_id = %d", contractID))
				.executeQuery();
		if(resultSet.next()) {
			return resultSet.getString("contract_status");
		}
		return "no";
	}
	
	private void delete(int contractID) throws SQLException, DeleteDebtorException {
		PreparedStatement ps = connection.prepareStatement(
						String.format(
						"DELETE FROM debtors " +
						"WHERE contract_id = %d", contractID));
		if (ps.execute()) {
			throw new DeleteDebtorException();
		}
	}

	private void addDebtor(int contractID) throws SQLException, AddDebtorException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO debtors (contract_id, contract_status) " +
								  "VALUES(?, ?)");
		ps.setInt(1, contractID);
		ps.setString(2, "not paid");

		if (ps.execute()) {
			throw new AddDebtorException();
		}
	}
}