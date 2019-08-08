package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.VariableArgument;

/**
 * Returns true if the next input argument is a variable type.
 *
 * @author TheDudeFromCI
 */
public class VariablePattern implements CommandTemplateArg
{
	@Override
	public int matchArguments(InputArgument[] args, int offset)
	{
		if (args.length <= offset)
			return -1;

		return args[offset].getArgument() instanceof VariableArgument ? 1 : -1;
	}
}
