package net.whg.awgenshell;

public interface CommandHandler
{
	String getName();

	String execute(ArgumentValue[] args);
}
