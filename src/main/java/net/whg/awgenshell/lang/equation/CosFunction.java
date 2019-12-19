package net.whg.awgenshell.lang.equation;

import ch.obermuhlner.math.big.BigFloat;

public class CosFunction implements IEquationFunction
{
	@Override
	public String getName()
	{
		return "cos";
	}

	@Override
	public Val solve(Val x)
	{
		if (x.type == Val.LIST || x.type == Val.VECTOR)
		{
			Val[] b = new Val[x.size];
			for (int i = 0; i < x.size; i++)
				b[i] = solve(x.get(i));

			return new Val(x.type, b);
		}

		if (x.type != Val.NUMBER)
			throw new EquationParserException(
					"Only number types, vectors of numbers, or lists of number types are supported here!");

		return new Val(BigFloat.cos(x.value));
	}
}
