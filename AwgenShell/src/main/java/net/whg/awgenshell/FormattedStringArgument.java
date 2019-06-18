package net.whg.awgenshell;

public class FormattedStringArgument implements ArgumentValue
{
	private String string;
	private ShellEnvironment env;

	public FormattedStringArgument(String string, ShellEnvironment env)
	{
		this.string = string;
		this.env = env;
	}

	@Override
	public String getValue()
	{
		String val = string;

		for (Variable v : env.getVariables())
			if (val.contains("$" + v.getName()))
				val = val.replace("$" + v.getName(), v.getValue());

		return val;
	}
}
