package commands;

public interface Command {
	
	public Command execute(Command parent);
}