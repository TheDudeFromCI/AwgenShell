package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;
import net.whg.awgenshell.lang.PrintCommand;
import net.whg.awgenshell.lang.RandomCommand;

public class Module
{
	static Module newLangModule()
	{
		Module m = new Module();

		m.loadCommand(new PrintCommand());
		m.loadCommand(new RandomCommand());

		return m;
	}

	private List<CommandHandler> commands = new ArrayList<>();

	public CommandHandler getCommand(String name)
	{
		for (CommandHandler c : commands)
			if (c.getName().equalsIgnoreCase(name))
				return c;

		for (CommandHandler c : commands)
			for (String a : c.getAliases())
				if (a.equalsIgnoreCase(name))
					return c;

		return null;
	}

	public void loadCommand(CommandHandler command)
	{
		commands.add(command);
	}
}
