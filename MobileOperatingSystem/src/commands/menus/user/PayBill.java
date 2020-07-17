package commands.menus.user;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import client.UserLogin;
import commands.Command;
import exceptions.PayBillException;

public class PayBill implements Command {
	private Connection connection;
	private PrintStream printOut;
	private UserLogin user;
	private Command nextCommand;

	public PayBill(Connection connection, PrintStream printOut, UserLogin user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextCommand;
	}
	@Override
	public Command execute(Command parent) {
		try {
			if (!checkPay()) {
				pay();
				printOut.println("Bill paid!");
			}else {
				printOut.println("You have already paid. Thank you!");
			}
			printOut.println("------------------");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PayBillException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}
	
	private void pay() throws SQLException, PayBillException {
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int today = localDate.getDayOfMonth();
		
		PreparedStatement ps = connection.prepareStatement(
						"UPDATE debtors " + 
						"JOIN contracts ON " + 
						"contracts.id = debtors.contract_id " + 
						"SET contract_status = 'paid', " +
						"pay_date = ? " +
						"WHERE contracts.phone = ?");
		ps.setInt(1, today);
		ps.setString(2, user.getPhone());

		if (ps.execute()) {
			throw new PayBillException();
		}
	}
	
	private boolean checkPay() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT contract_status " +
				"FROM debtors d " +
				"JOIN contracts c ON " + 
				"d.contract_id = c.id " +
				"WHERE phone LIKE '%s'", user.getPhone()))
				.executeQuery();
		
		if (resultSet.next()) {
			if(resultSet.getString("contract_status").equals("paid")) {
				return true;
			}
		}
		return false;
	}
}