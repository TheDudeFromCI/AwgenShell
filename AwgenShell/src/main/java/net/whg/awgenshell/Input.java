package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a set of expressions, given as an input.
 *
 * @author TheDudeFromCI
 */
class Input
{
	/**
	 * A single expression element within this input, and the given expression
	 * seperator between it and the next expression.
	 *
	 * @author TheDudeFromCI
	 */
	static class ExpressionSequence
	{
		Expression expression;
		ExpressionSeperator seperator;

		ExpressionSequence(Expression expression)
		{
			this.expression = expression;
			seperator = ExpressionSeperator.NORMAL;
		}
	}

	/**
	 * The type of seperator between two expression to apply.
	 *
	 * @author TheDudeFromCI
	 */
	enum ExpressionSeperator
	{
		NORMAL,
		AND,
		OR,
	}

	/**
	 * Attempts to create an input grammer by consuming as many tokens as possbile.
	 *
	 * @param env
	 *     - The environment to compile the tokens in.
	 * @param tokenizer
	 *     - The tokenizer to supply the tokens.
	 * @return An input if one could be constructed, or null if an input grammer
	 *     could not be made.
	 */
	static Input consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		Input input = null;

		if (tokenizer.hasNextToken())
		{
			Expression expression = Expression.consumeTokens(env, tokenizer);

			if (expression == null)
				return null;

			input = new Input(env);
			input.appendExpression(expression);

			while (tokenizer.hasNextToken())
			{
				Token token = tokenizer.peekNextToken();

				if (token.getType() == TokenTemplate.SEMICOLON_SYMBOL)
				{
					tokenizer.consumeToken();

					expression = Expression.consumeTokens(env, tokenizer);
					if (expression == null)
						return input;
					input.appendExpression(expression);
					input.setSeperator(ExpressionSeperator.NORMAL);
				}
				else if (token.getType() == TokenTemplate.AND_SYMBOL)
				{
					tokenizer.consumeToken();

					expression = Expression.consumeTokens(env, tokenizer);
					if (expression == null)
						throw new CommandParseException("Unexpected token: " + tokenizer.nextToken().getValue() + "!");
					input.appendExpression(expression);
					input.setSeperator(ExpressionSeperator.AND);
				}
				else if (token.getType() == TokenTemplate.PIPE_SYMBOL)
				{
					tokenizer.consumeToken();

					expression = Expression.consumeTokens(env, tokenizer);
					if (expression == null)
						throw new CommandParseException("Unexpected token: " + tokenizer.nextToken().getValue() + "!");
					input.appendExpression(expression);
					input.setSeperator(ExpressionSeperator.OR);
				}
				else
					break;
			}
		}

		return input;
	}

	private ShellEnvironment env;
	private List<ExpressionSequence> expressions = new ArrayList<>();

	/**
	 * Creates a new Input grammer instance.
	 *
	 * @param env
	 *     - The environment this grammer is compiled for.
	 */
	Input(ShellEnvironment env)
	{
		this.env = env;
	}

	/**
	 * Adds an expression to this input set.
	 *
	 * @param expression
	 *     - The expression.
	 */
	private void appendExpression(Expression expression)
	{
		expressions.add(new ExpressionSequence(expression));
	}

	/**
	 * Sets the seperator between the previously added expression and the next
	 * expression.
	 *
	 * @param seperator
	 *     - The type of seperator.
	 */
	private void setSeperator(ExpressionSeperator seperator)
	{
		if (expressions.size() == 0)
			return;

		expressions.get(expressions.size() - 1).seperator = seperator;
	}

	/**
	 * Executes all of the expressions within this input set.
	 *
	 * @param isDirectCommand
	 *     - True if this input is being executed from within a direct command,
	 *     false otherwise.
	 * @return The result of the last expression within this set.
	 */
	CommandResult execute(boolean isDirectCommand)
	{
		ExpressionSeperator last = ExpressionSeperator.NORMAL;
		boolean lastState = true;
		CommandResult response = null;

		for (int i = 0; i < expressions.size(); i++)
		{
			ExpressionSequence seq = expressions.get(i);

			if (last == ExpressionSeperator.NORMAL || last == ExpressionSeperator.AND && lastState
					|| last == ExpressionSeperator.OR && !lastState)
			{
				response = seq.expression.execute(env, isDirectCommand);
				lastState = response.isNormalExit();
			}
			else
				lastState = false;

			last = seq.seperator;
		}

		if (response == null) // Empty expression list
			response = new CommandResult("", true, true);

		return response;
	}
}
