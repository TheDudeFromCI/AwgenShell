package net.whg.awgenshell;

/**
 * This class represents a virtual environment to execute commands within.
 *
 * @author TheDudeFromCI
 */
public class ShellEnvironment
{
	private CommandList commandList;

	public ShellEnvironment()
	{
		commandList = new CommandList();
	}

	public void executeCommand(CommandSender sender, String cmd)
	{
		CommandSet commandSet = CommandParser.parse(commandList, sender, cmd);
		commandList.executeCommandSet(commandSet);
	}
}
