package net.whg.awgenshell;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		if (!printVersion())
			return;

		ShellEnvironment env = new ShellEnvironment();

		Scanner scan = new Scanner(System.in);
		while (true)
		{
			String line = scan.nextLine();

			if (line.equalsIgnoreCase("exit"))
				break;

			try
			{
				Input in = CommandParser.parse(env, line);
				System.out.println(in.execute(env).getValue());
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
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
}
