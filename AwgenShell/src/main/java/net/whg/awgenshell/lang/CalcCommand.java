package net.whg.awgenshell.lang;

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
	private static double eval(final String str)
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

			double parse()
			{
				nextChar();
				double x = parseExpression();
				if (pos < str.length())
					throw new RuntimeException("Unexpected: " + (char) ch);
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			// | number | functionName factor | factor `^` factor

			double parseExpression()
			{
				double x = parseTerm();
				for (;;)
				{
					if (eat('+'))
						x += parseTerm(); // addition
					else if (eat('-'))
						x -= parseTerm(); // subtraction
					else
						return x;
				}
			}

			double parseTerm()
			{
				double x = parseFactor();
				for (;;)
				{
					if (eat('*'))
						x *= parseFactor(); // multiplication
					else if (eat('/'))
						x /= parseFactor(); // division
					else
						return x;
				}
			}

			double parseFactor()
			{
				if (eat('+'))
					return parseFactor(); // unary plus
				if (eat('-'))
					return -parseFactor(); // unary minus

				double x;
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
					x = Double.parseDouble(str.substring(startPos, pos));
				}
				else if (ch >= 'a' && ch <= 'z')
				{ // functions
					while (ch >= 'a' && ch <= 'z')
						nextChar();
					String func = str.substring(startPos, pos);
					x = parseFactor();
					if (func.equals("sqrt"))
						x = Math.sqrt(x);
					else if (func.equals("sin"))
						x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos"))
						x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan"))
						x = Math.tan(Math.toRadians(x));
					else if (func.equals("floor"))
						x = Math.floor(x);
					else if (func.equals("ceil"))
						x = Math.ceil(x);
					else if (func.equals("round"))
						x = Math.round(x);
					else
						throw new RuntimeException("Unknown function: " + func);
				}
				else
				{
					throw new RuntimeException("Unexpected: " + (char) ch);
				}

				if (eat('^'))
					x = Math.pow(x, parseFactor()); // exponentiation

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
