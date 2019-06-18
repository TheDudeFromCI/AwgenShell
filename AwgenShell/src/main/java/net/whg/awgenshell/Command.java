package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

public class Command implements GrammerStack
{
	private String commandName;
	private List<Argument> arguments = new ArrayList<>();

	public String execute(ShellEnvironment environment)
	{
		for (Argument arg : arguments)
			if (arg.getValue() instanceof CommandArgument)
				((CommandArgument) arg.getValue()).evaluate();

		// TODO
		return commandName;
	}

	@Override
	public boolean consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		Token commandName = tokenizer.nextToken();

		if (commandName.getType() != TokenTemplate.SOFT_STRING)
			return false;

		this.commandName = commandName.getFormattedValue();

		if (tokenizer.hasNextToken())
		{
			Argument argument = new Argument();
			if (argument.consumeTokens(env, tokenizer))
			{
				arguments.add(argument);

				while (tokenizer.hasNextToken())
				{
					Token comma = tokenizer.peekNextToken();

					if (comma.getType() == TokenTemplate.COMMA_SYMBOL)
					{
						argument = new Argument();
						if (argument.consumeTokens(env, tokenizer))
							arguments.add(argument);
						else
							throw new CommandParseException(
									"Unexpected token: " + tokenizer.nextToken().getValue() + "!");
					}
					else
					{
						argument = new Argument();
						if (argument.consumeTokens(env, tokenizer))
							arguments.add(argument);
						else
							break;
					}
				}
			}
		}

		return true;
	}
}
