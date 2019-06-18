package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;
import net.whg.awgenshell.lang.PrintCommand;

public class Module
{
	static Module newLangModule()
	{
		Module m = new Module();

		m.loadCommand(new PrintCommand());

		return m;
	}

	private List<CommandHandler> commands = new ArrayList<>();

	public CommandHandler getCommand(String name)
	{
		for (CommandHandler c : commands)
			if (c.getName().equals(name))
				return c;
		return null;
	}

	public void loadCommand(CommandHandler command)
	{
		commands.add(command);
	}
}
