package net.whg.awgenshell;

import java.io.IOException;
import java.util.Properties;

public class Main
{
	public static void main(String[] args)
	{
		if (!printVersion())
			return;
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
