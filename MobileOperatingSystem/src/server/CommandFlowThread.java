package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import commands.Command;
import commands.menus.MainMenuCommand;

public class CommandFlowThread extends Thread{
	private Socket socket;
	private String address = "jdbc:mysql://localhost:3306/mobile_operator_db";
	
	public CommandFlowThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		super.run();
		Command command = null;
		try {
			Connection connection = DriverManager.getConnection(address, "root", "1234");
			PrintStream printOut = new PrintStream(this.socket.getOutputStream());
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			command = new MainMenuCommand(connection, printOut, buffReader);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Command previousCommand = null;
		while(command != null) {
			Command newCommand = command.execute(previousCommand);
			previousCommand = command;
			command = newCommand;
		}
	}
}