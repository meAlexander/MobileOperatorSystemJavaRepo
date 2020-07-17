package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1211);

		while (true) {
			Socket socket = server.accept();

			CommandFlowThread thread = new CommandFlowThread(socket);
			thread.setDaemon(true);
			thread.start();
		}
	}
}