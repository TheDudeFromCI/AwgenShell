package net.whg.awgenshell.lang;

import ch.obermuhlner.math.big.BigFloat;
import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.ShellEnvironment;

/**
 * Caclulates the value of a mathmatical formula given as an input. Supports
 * basic mathmatical functions: add, subtract, multiply, divide, raise to a
 * power, sqrt, sin, cos, tan, floor, ceil, and round.
 *
 * @author TheDudeFromCI
 * @author Boann (https://stackoverflow.com/a/26227947/1610708)
 */
public class CalcCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"eval"
	};

	/**
	 * Evaluates a string as mathmatical equation.
	 *
	 * @param str
	 *     - The string to evaluate.
	 * @return The final output of the equation.
	 */
	private static BigFloat eval(final String str)
	{
		return new Object()
		{
			int pos = -1, ch;

			void nextChar()
			{
				ch = ++pos < str.length() ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat)
			{
				while (ch == ' ')
					nextChar();
				if (ch == charToEat)
				{
					nextChar();
					return true;
				}
				return false;
			}

			BigFloat parse()
			{
				nextChar();
				BigFloat x = parseExpression();
				if (pos < str.length())
					throw new RuntimeException("Unexpected: " + (char) ch);
				return x;
			}

			BigFloat parseExpression()
			{
				BigFloat x = parseTerm();

				while (true)
				{
					if (eat('+'))
						x = x.add(parseTerm());
					else if (eat('-'))
						x = x.subtract(parseTerm());
					else
						return x;
				}
			}

			BigFloat parseTerm()
			{
				BigFloat x = parseFactor();
				for (;;)
				{
					if (eat('*'))
						x = x.multiply(parseFactor());
					else if (eat('/'))
						x = x.divide(parseFactor());
					else
						return x;
				}
			}

			BigFloat floor(BigFloat x)
			{
				return x.isNegative()
						? x.getFractionalPart().isZero() ? x.getIntegralPart() : x.getIntegralPart().subtract(1)
						: x.getIntegralPart();
			}

			BigFloat ceil(BigFloat x)
			{
				return x.isNegative() ? x.getIntegralPart()
						: x.getFractionalPart().isZero() ? x.getIntegralPart() : x.getIntegralPart().add(1);
			}

			BigFloat round(BigFloat x)
			{
				return x.getFractionalPart().isGreaterThanOrEqual(BigFloat.context(128).valueOf(0.5)) ? ceil(x)
						: floor(x);
			}

			BigFloat parseFactor()
			{
				if (eat('+'))
					return parseFactor(); // unary plus
				if (eat('-'))
					return parseFactor().multiply(-1); // unary minus

				BigFloat x;
				int startPos = pos;
				if (eat('('))
				{ // parentheses
					x = parseExpression();
					eat(')');
				}
				else if (ch >= '0' && ch <= '9' || ch == '.')
				{ // numbers
					while (ch >= '0' && ch <= '9' || ch == '.')
						nextChar();
					x = BigFloat.context(128).valueOf(str.substring(startPos, pos));
				}
				else if (ch >= 'a' && ch <= 'z')
				{ // functions
					while (ch >= 'a' && ch <= 'z')
						nextChar();
					String func = str.substring(startPos, pos);
					x = parseFactor();
					if (func.equals("sqrt"))
						x = BigFloat.sqrt(x);
					else if (func.equals("sin"))
						x = BigFloat.sin(x);
					else if (func.equals("cos"))
						x = BigFloat.cos(x);
					else if (func.equals("tan"))
						x = BigFloat.tan(x);
					else if (func.equals("floor"))
						x = floor(x);
					else if (func.equals("ceil"))
						x = ceil(x);
					else if (func.equals("round"))
						x = round(x);
					else
						throw new RuntimeException("Unknown function: " + func);
				}
				else
				{
					throw new RuntimeException("Unexpected: " + (char) ch);
				}

				if (eat('^'))
					x = x.pow(parseFactor());

				return x;
			}
		}.parse();
	}

	@Override
	public String getName()
	{
		return "calc";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (args.length != 1)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		try
		{
			String val = String.valueOf(eval(args[0].getValue()));
			return new CommandResult(val, true, false);
		}
		catch (Exception exception)
		{
			env.getCommandSender().println("Failed to parse equation! " + exception.getMessage());
			return CommandResult.ERROR;
		}
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
