package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

class Input implements GrammerStack
{
	private class ExpressionSequence
	{
		Expression expression;
		ExpressionSeperator seperator;

		ExpressionSequence(Expression expression)
		{
			this.expression = expression;
			seperator = ExpressionSeperator.NORMAL;
		}
	}

	public enum ExpressionSeperator
	{
		NORMAL,
		AND,
		OR,
	}

	private List<ExpressionSequence> expressions = new ArrayList<>();

	public void appendExpression(Expression expression)
	{
		expressions.add(new ExpressionSequence(expression));
	}

	public void setSeperator(ExpressionSeperator seperator)
	{
		if (expressions.size() == 0)
			return;

		expressions.get(expressions.size() - 1).seperator = seperator;
	}

	public CommandResult execute(ShellEnvironment environment, boolean isDirectCommand)
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
				response = seq.expression.execute(environment, isDirectCommand);
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

	public int getExpressionCount()
	{
		return expressions.size();
	}

	public Expression getExpression(int index)
	{
		return expressions.get(index).expression;
	}

	@Override
	public boolean consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		boolean consumed = false;

		if (tokenizer.hasNextToken())
		{
			Expression expression = new Expression();

			if (!expression.consumeTokens(env, tokenizer))
				return false;

			appendExpression(expression);
			consumed = true;

			while (tokenizer.hasNextToken())
			{
				Token token = tokenizer.peekNextToken();

				if (token.getType() == TokenTemplate.SEMICOLON_SYMBOL)
				{
					tokenizer.consumeToken();

					expression = new Expression();
					if (!expression.consumeTokens(env, tokenizer))
						return true;
					appendExpression(expression);
				}
				else if (token.getType() == TokenTemplate.AND_SYMBOL)
				{
					tokenizer.consumeToken();

					expression = new Expression();
					if (!expression.consumeTokens(env, tokenizer))
						throw new CommandParseException("Unexpected token: " + tokenizer.nextToken().getValue() + "!");
					appendExpression(expression);
				}
				else if (token.getType() == TokenTemplate.PIPE_SYMBOL)
				{
					tokenizer.consumeToken();

					expression = new Expression();
					if (!expression.consumeTokens(env, tokenizer))
						throw new CommandParseException("Unexpected token: " + tokenizer.nextToken().getValue() + "!");
					appendExpression(expression);
				}
				else
					break;
			}
		}

		return consumed;
	}
}
