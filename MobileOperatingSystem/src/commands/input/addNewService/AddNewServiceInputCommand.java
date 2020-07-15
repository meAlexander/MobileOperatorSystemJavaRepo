package commands.input.addNewService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.addNewService.AddNewServiceActionCommand;

public class AddNewServiceInputCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private Command nextCommand;

	public AddNewServiceInputCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.nextCommand = nextCommand;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Enter service(MIN, MB, SMS)");
			printOut.println("Your input please: ");
			printOut.flush();
			String service = buffReader.readLine();
			
			printOut.println("Enter service limit");
			printOut.println("Your input please: ");
			printOut.flush();
			double limit = Double.parseDouble(buffReader.readLine());
			
			printOut.println("Enter bill");
			printOut.println("Your input please: ");
			printOut.flush();
			double bill = Double.parseDouble(buffReader.readLine());
			
			return new AddNewServiceActionCommand(connection, printOut, service, limit, bill, nextCommand);
		} catch (NumberFormatException e) {
			printOut.println("Service limit has to be a number!");
			printOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nextCommand;
	}	
}