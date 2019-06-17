package net.whg.awgenshell;

public class CommandExecution
{
	private Command command;
	private Variable output;

	public CommandExecution(Command command, Variable output)
	{
		this.command = command;
		this.output = output;
	}

	public Command getCommand()
	{
		return command;
	}

	public Variable getOutput()
	{
		return output;
	}
}
