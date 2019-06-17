package net.whg.awgenshell;

public class CommandExecution
{
	private Command command;
	private CommandVariable output;

	public CommandExecution(Command command, CommandVariable output)
	{
		this.command = command;
		this.output = output;
	}

	public Command getCommand()
	{
		return command;
	}

	public CommandVariable getOutput()
	{
		return output;
	}
}
