package net.whg.awgenshell.util.template;

/**
 * Checks for indirect command arguments. Returns false for all other inputs.
 *
 * @author TheDudeFromCI
 */
public class IndirectCommandPattern implements CommandTemplateArg
{
	@Override
	public int matchArguments(InputArgument[] args, int offset)
	{
		if (args.length <= offset)
			return -1;

		return args[offset].isIndirectCommand() ? 1 : -1;
	}
}
