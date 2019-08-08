package net.whg.awgenshell.util.template;

import net.whg.awgenshell.perms.PermissionNode;

/**
 * Represents a compiled subcommand which checks if a given set of inputs match
 * this subcommand pattern.
 *
 * @author TheDudeFromCI
 */
public class SubCommand
{
	public static SubCommand compile(String pattern, SubCommandExecutor executor, PermissionNode permission)
	{
		String[] parts = pattern.split("\\s");
		CommandTemplateArg[] args = new CommandTemplateArg[parts.length];

		for (int i = 0; i < args.length; i++)
			args[i] = compileArg(parts[i]);

		return new SubCommand(args, executor, permission);

	}

	private static CommandTemplateArg compileArg(String word)
	{
		if (word.contains("|"))
		{
			String[] subwords = word.split("\\|");
			CommandTemplateArg[] subpatterns = new CommandTemplateArg[subwords.length];

			for (int i = 0; i < subwords.length; i++)
				subpatterns[i] = compileArg(subwords[i]);

			return new OrPattern(subpatterns);
		}

		if (word.startsWith("%"))
		{
			word = word.substring(1);

			if (word.equals("n"))
				return new EndOfLinePattern();

			if (word.equals("*"))
				return new WildcardPattern(false, false);

			if (word.equals("**"))
				return new WildcardPattern(true, true);

			if (word.equals("*+"))
				return new WildcardPattern(true, false);

			if (word.equals("*?"))
				return new WildcardPattern(false, true);

			if (word.equals("#"))
				return new NumberPattern(false);

			if (word.equals("#f"))
				return new NumberPattern(true);

			if (word.equals("{}"))
				return new IndirectCommandPattern();

			throw new IllegalArgumentException("Unknown pattern type! %" + word);
		}
		else
			return new DirectWordPattern(word);
	}

	private final CommandTemplateArg[] pattern;
	private final SubCommandExecutor executor;
	private final PermissionNode permission;

	/**
	 * Creates a new subcommand with a given of compiled template arguments.
	 *
	 * @param pattern
	 *     - An array of compiled template arguments.
	 * @param executor
	 *     - The executor for this subcommand.
	 */
	public SubCommand(CommandTemplateArg[] pattern, SubCommandExecutor executor, PermissionNode permission)
	{
		this.pattern = pattern;
		this.executor = executor;
		this.permission = permission;
	}

	/**
	 * Checks if the given set of inputs arguments matches this subcommand.
	 *
	 * @param args
	 *     - The input arguments
	 * @return True if this subcommand is a valid match, false otherwise.
	 */
	public boolean matches(InputArgument[] args)
	{
		int offset = 0;
		for (CommandTemplateArg a : pattern)
		{
			int out = a.matchArguments(args, offset);
			if (out < 0)
				return false;

			offset += out;
		}

		return true;
	}

	/**
	 * Gets the executor attached to this subcommand.
	 *
	 * @return - The executor.
	 */
	public SubCommandExecutor getExecutor()
	{
		return executor;
	}

	/**
	 * Gets the permission node required to use this subcommand. Value returns null
	 * if the parent permission node of the command is intended to be used instead.
	 *
	 * @return The required permission node. May be null.
	 */
	public PermissionNode getPermissions()
	{
		return permission;
	}
}
