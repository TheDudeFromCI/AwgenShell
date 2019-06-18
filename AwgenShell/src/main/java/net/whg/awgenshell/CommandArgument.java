package net.whg.awgenshell;

public class CommandArgument implements ArgumentValue
{
	private Input input;
	private ShellEnvironment env;
	private boolean direct;
	private String value;

	public CommandArgument(Input input, ShellEnvironment env, boolean direct)
	{
		this.input = input;
		this.env = env;
		this.direct = direct;
	}

	public boolean isDirect()
	{
		return direct;
	}

	public void evaluate()
	{
		if (direct)
			getValue();
	}

	@Override
	public String getValue()
	{
		if (direct && value != null)
			return value;

		return value = input.execute(env).getValue();
	}
}
