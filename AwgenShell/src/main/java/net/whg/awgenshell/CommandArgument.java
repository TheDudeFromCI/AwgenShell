package net.whg.awgenshell;

/**
 * An input argument for a command which, in itself, is also a command. This
 * class can either be a direct command or indirect command.
 *
 * @author TheDudeFromCI
 */
public class CommandArgument implements ArgumentValue
{
	private Input input;
	private ShellEnvironment env;
	private boolean direct;
	private String value;

	/**
	 * Creates a new command argument instance.
	 *
	 * @param input
	 *     - The command that is stored within this argument to execute.
	 * @param env
	 *     - The environment to execute the command in.
	 * @param direct
	 *     - Whether or not this command is direct. If this command is direct, the
	 *     command is only executed a single time (right before the command this
	 *     argument is for is executed) and that value is used repeatedly instead.
	 *     If this command is indirect, the given input command is run each time the
	 *     value is used.
	 */
	public CommandArgument(Input input, ShellEnvironment env, boolean direct)
	{
		this.input = input;
		this.env = env;
		this.direct = direct;
	}

	/**
	 * Checks if this command argument is marked as a direct command.
	 *
	 * @return True if this command is a direct command, false otherwise.
	 */
	public boolean isDirect()
	{
		return direct;
	}

	/**
	 * If this command is direct, this method executes the input command and stores
	 * the value for reused. If this argument is an indirect command, or if the
	 * value has already been calculated, nothing happens.
	 */
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

		return value = input.execute(env, direct).getValue();
	}
}
