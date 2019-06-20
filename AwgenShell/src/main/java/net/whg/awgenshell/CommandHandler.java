package net.whg.awgenshell;

public interface CommandHandler
{
	String getName();

	String execute(CommandSender sender, ArgumentValue[] args);

	String[] getAliases();
}
