package net.whg.awgenshell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandManager
{
	private static Logger logger = LoggerFactory.getLogger(CommandManager.class);

	private CommandList commandList;
	private VariableKeyring variableKeyRing;

	public CommandManager()
	{
		commandList = new CommandList();
		variableKeyRing = new VariableKeyring();
	}

	public CommandList getCommandList()
	{
		return commandList;
	}

	public VariableKeyring getVariableKeyring()
	{
		return variableKeyRing;
	}

	public void execute(String command, CommandSender sender)
	{
		try
		{
			CommandSet commandSet = CommandParser.parse(commandList, sender, command);
			commandList.executeCommandSet(commandSet);
		}
		catch (CommandParseException exception)
		{
			sender.println("Failed to parse command '" + command + "'!");
		}
		catch (Exception exception)
		{
			sender.println("An error has occured while executing this command.!");
			logger.error(String.format("Error while preforming command! '%s'", command), exception);
		}
	}
}
