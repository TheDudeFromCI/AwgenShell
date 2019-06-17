package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandList
{
	private static Logger logger = LoggerFactory.getLogger(CommandList.class);

	private List<CommandHandler> commands = new ArrayList<>();

	public void addCommand(CommandHandler handler)
	{
		if (handler == null)
			return;

		commands.add(handler);
	}

	public void removeCommand(CommandHandler handler)
	{
		if (handler == null)
			return;

		commands.remove(handler);
	}

	public void executeCommandSet(CommandSet set)
	{
		for (CommandExecution exe : set.getCommandExecutions())
		{
			Command cmd = exe.getCommand();
			CommandHandler handler = getCommand(cmd.getName());

			if (handler == null)
			{
				cmd.getCommandSender().println("Unknown command '" + cmd.getName() + "'");
				continue;
			}

			try
			{
				String out = handler.executeCommand(cmd);
				exe.getOutput().setValue(out);
			}
			catch (Exception exception)
			{
				logger.error(String.format("Failed to execute command '%s'!", cmd), exception);
			}
		}
	}

	public CommandHandler getCommand(String name)
	{
		for (CommandHandler handler : commands)
		{
			if (handler.getCommandName().equals(name))
				return handler;
		}

		for (CommandHandler handler : commands)
		{
			for (String s : handler.getCommandAliases())
				if (s.equals(name))
					return handler;
		}

		return null;
	}

	public int getCommandCount()
	{
		return commands.size();
	}

	public CommandHandler getCommand(int index)
	{
		return commands.get(index);
	}
}
