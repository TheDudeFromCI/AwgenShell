package net.whg.awgenshell.util.template;

import java.util.List;
import net.whg.awgenshell.arg.VariableArgument;

/**
 * Returns true if the next input argument is a variable type.
 *
 * @author TheDudeFromCI
 */
public class VariablePattern implements CommandTemplateArg
{
	@Override
	public int matchArguments(List<InputArgument> args, int offset, SubCommand sub)
	{
		if (args.size() <= offset)
			return -1;

		return args.get(offset).getArgument() instanceof VariableArgument ? 1 : -1;
	}
}
