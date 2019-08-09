package net.whg.awgenshell.util.template;

import java.util.List;

/**
 * Checks for indirect command arguments. Returns false for all other inputs.
 *
 * @author TheDudeFromCI
 */
public class IndirectCommandPattern implements CommandTemplateArg
{
	@Override
	public int matchArguments(List<InputArgument> args, int offset, SubCommand sub)
	{
		if (args.size() <= offset)
			return -1;

		return args.get(offset).isIndirectCommand() ? 1 : -1;
	}
}
