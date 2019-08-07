package net.whg.awgenshell.lang.equation;

import java.util.ArrayList;

/**
 * This class parses a string into a mathmatical equation and solves it.
 *
 * @author TheDudeFromCI
 * @author Boann (https://stackoverflow.com/a/26227947/1610708)
 */
public class EquationSolver
{
	private final ArrayList<IEquationFunction> functions = new ArrayList<>();
	private String str;
	private int pos;
	private int ch;

	public EquationSolver()
	{
		functions.add(new CeilFunction());
		functions.add(new ComplexFunction());
		functions.add(new CosFunction());
		functions.add(new FloorFunction());
		functions.add(new RoundFunction());
		functions.add(new SinFunction());
		functions.add(new SqrtFunction());
		functions.add(new TanFunction());
		functions.add(new VectorFunction());
	}

	/**
	 * Adds a new type of function to this equation solver.
	 *
	 * @param function
	 *     - The function to add.
	 */
	public void addFunction(IEquationFunction function)
	{
		if (function == null)
			return;

		if (functions.contains(function))
			return;

		functions.add(function);
	}

	/**
	 * Gets the function in this solver with the given name. Names are not case
	 * sensitive.
	 *
	 * @param name
	 *     - The name of the function.
	 * @return The function with the given name, or null if this solver does not
	 *     have any functions with the given name.
	 */
	public IEquationFunction getFunction(String name)
	{
		for (IEquationFunction func : functions)
			if (func.getName().equalsIgnoreCase(name))
				return func;
		return null;
	}

	/**
	 * Parses the given string into a BigFloat representation.
	 *
	 * @param str
	 *     - The string to parse and solve.
	 * @return A list of BigFloats representing the answer. If the equation is
	 *     seperated by commas, each section of the eqution is solved and returned.
	 *     Useful for handling vector math.
	 * @throws EquationParserException
	 *     - If the given equation could not be parsed.
	 */
	public Val parse(String str)
	{
		this.str = str;
		pos = -1;

		nextChar();
		Val x = parseExpressionList();

		if (pos < str.length())
			throw new EquationParserException("Unexpected: " + (char) ch);

		return x;
	}

	private void nextChar()
	{
		ch = ++pos < str.length() ? str.charAt(pos) : -1;
	}

	private boolean eat(int charToEat)
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

	private Val parseExpression()
	{
		Val x = parseTerm();

		while (true)
		{
			if (eat('+'))
				x = Val.add(x, parseTerm());
			else if (eat('-'))
				x = Val.sub(x, parseTerm());
			else
				return x;
		}
	}

	private Val parseTerm()
	{
		Val x = parseFactor();

		while (true)
		{
			if (eat('*'))
				x = Val.mul(x, parseFactor());
			else if (eat('/'))
				x = Val.div(x, parseFactor());
			else if (eat('%'))
				x = Val.mod(x, parseFactor());
			else
				return x;
		}
	}

	private Val parseExpressionList()
	{
		Val x = Val.EMPTY_LIST;

		do
			x = Val.append(x, parseExpression());
		while (eat(','));

		if (x.size == 1)
			return x.get(0);

		return x;
	}

	private Val parseFactor()
	{
		if (eat('+'))
			return parseFactor();
		if (eat('-'))
			return Val.mul(parseFactor(), Val.NEGATIVE_ONE);

		Val x;
		int startPos = pos;

		if (eat('('))
		{
			x = parseExpressionList();
			eat(')');
		}
		else if (ch >= '0' && ch <= '9' || ch == '.')
		{
			while (ch >= '0' && ch <= '9' || ch == '.')
				nextChar();

			x = new Val(str.substring(startPos, pos));
		}
		else if (ch >= 'a' && ch <= 'z' || ch == '_')
		{
			while (ch >= 'a' && ch <= 'z' || ch == '_')
				nextChar();

			String func = str.substring(startPos, pos);
			x = parseFactor();

			IEquationFunction function = getFunction(func);
			if (function != null)
				x = function.solve(x);
			else
				throw new EquationParserException("Unknown function: " + func);
		}
		else
			throw new EquationParserException("Unexpected: " + (char) ch);

		if (eat('^'))
			x = Val.pow(x, parseFactor());

		return x;
	}
}
