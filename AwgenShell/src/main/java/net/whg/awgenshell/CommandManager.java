package net.whg.awgenshell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandManager
{
	private static Logger logger = LoggerFactory.getLogger(CommandManager.class);

	private CommandList _commandList;
	private VariableKeyring _variableKeyRing;

	public CommandManager()
	{
		_commandList = new CommandList();

		_variableKeyRing = new VariableKeyring();
	}

	public CommandList getCommandList()
	{
		return _commandList;
	}

	public VariableKeyring getVariableKeyring()
	{
		return _variableKeyRing;
	}

	public void execute(String command, CommandSender sender)
	{
		try
		{
			CommandSet commandSet = CommandParser.parse(_commandList, sender, command);
			_commandList.executeCommandSet(commandSet);
		}
		catch (CommandParseException exception)
		{
			sender.getConsole().println("Failed to parse command '" + command + "'!");
		}
		catch (Exception exception)
		{
			sender.getConsole().println("An error has occured while executing this command.!");
			logger.error(String.format("Error while preforming command! '%s'", command), exception);
		}
	}
}
