package net.whg.awgenshell;

public class Command
{
	private String name;
	private CommandArgument[] args;
	private CommandSender sender;

	public Command(String name, CommandArgument[] args, CommandSender sender)
	{
		this.name = name;
		this.args = args;
		this.sender = sender;
	}

	public String getName()
	{
		return name;
	}

	public CommandArgument[] getArgs()
	{
		return args;
	}

	public void setArg(int index, CommandArgument value)
	{
		args[index] = value;
	}

	public CommandArgument getArg(int index)
	{
		return args[index];
	}

	public CommandSender getCommandSender()
	{
		return sender;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(name);

		for (CommandArgument s : args)
			sb.append(" ").append(s.getValue());

		return sb.toString();
	}
}
