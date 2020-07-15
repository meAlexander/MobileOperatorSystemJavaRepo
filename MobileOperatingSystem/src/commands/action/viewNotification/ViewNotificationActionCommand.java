package commands.action.viewNotification;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import client.UserLogin;
import commands.Command;

public class ViewNotificationActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private UserLogin user;
	private Command nextCommand;

	public ViewNotificationActionCommand(Connection connection, PrintStream printOut, UserLogin user, Command nextComand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextComand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			if(checkPaymentDate() <= 3) {
				printOut.println("You have days to pay or your services will be stopped! Thank you if you have already paid");
				printOut.flush();
			}else {
				printOut.println("No messages");
				printOut.flush();
			}
			printOut.println("-------------");;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nextCommand;
	}
	
	private int getPaymentDate() throws SQLException {
		int paymentDate = 0;
		ResultSet resultSet = connection.prepareStatement(
				String.format(
				"SELECT payment_date " + 
				"FROM contracts co " +
				"WHERE phone LIKE '%s'", user.getPhone()))
				.executeQuery();
		
		if (resultSet.next()) {
			paymentDate = resultSet.getInt("payment_date");
		}
		return paymentDate;
	}
	
	private long checkPaymentDate() throws SQLException, ParseException {
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	    Date firstDate = sdf.parse(month + "/" + getPaymentDate() + "/" + year);
	    Date secondDate = sdf.parse(month + "/" + day + "/" + year);
	 
	    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    
		return diff;
	}
}