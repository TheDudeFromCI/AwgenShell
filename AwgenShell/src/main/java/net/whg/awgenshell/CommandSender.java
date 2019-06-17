package net.whg.awgenshell;

public interface CommandSender
{
	CommandConsole getConsole();

	VariableKeyring getVariableKeyring();

	String getUsername();

	void println(String message);
}
