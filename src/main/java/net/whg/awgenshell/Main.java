package net.whg.awgenshell;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;

class Main
{
	public static void main(String[] args)
	{
		if (!printVersion())
			return;

		TerminalCommandSender sender = new TerminalCommandSender();
		ShellEnvironment env = new ShellEnvironment(sender);

		Scanner scan = new Scanner(System.in);
		while (true)
		{
			String line = scan.nextLine();

			if (line.equalsIgnoreCase("exit"))
				break;

			env.runCommand(line);
		}

		scan.close();
	}

	private static boolean printVersion()
	{
		try
		{
			System.out.println("Awgen Shell");

			Properties props = new Properties();
			props.load(Main.class.getClassLoader().getResourceAsStream("project.properties"));

			System.out.printf("version: %s\n\n", props.getProperty("project.version"));
			return true;
		}
		catch (IOException exception)
		{
			System.err.println("Failed to load project properties!");
			exception.printStackTrace();
			return false;
		}
	}

	private static class TerminalCommandSender implements CommandSender
	{
		@Override
		public void println(String message)
		{
			System.out.println(message);
		}

		@Override
		public String getName()
		{
			return "Console";
		}
	}
}
