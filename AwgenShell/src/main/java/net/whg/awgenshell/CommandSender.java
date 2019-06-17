package net.whg.awgenshell;

public interface CommandSender
{
	VariableKeyring getVariableKeyring();

	String getUsername();

	void println(String message);
}
