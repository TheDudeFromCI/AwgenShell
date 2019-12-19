package net.whg.awgenshell.lang.equation;

public class ComplexFunction implements IEquationFunction
{
	@Override
	public String getName()
	{
		return "c";
	}

	@Override
	public Val solve(Val x)
	{
		if (x.type == Val.VECTOR || x.type == Val.COMPLEX_NUMBER)
			throw new EquationParserException("Complex numbers may only contain numbers!");

		if (x.type == Val.NUMBER)
			throw new EquationParserException("Complex numbers must contain two values!");

		if (x.size != 2)
			throw new EquationParserException("Complex numbers must contain two values!");

		Val[] v = new Val[x.size];

		for (int i = 0; i < x.size; i++)
		{
			if (x.get(i).type != Val.NUMBER)
				throw new EquationParserException("Complex numbers may only contain numbers!");

			v[i] = x.get(i);
		}

		return new Val(Val.COMPLEX_NUMBER, v);
	}
}
