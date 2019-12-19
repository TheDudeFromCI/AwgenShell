package net.whg.awgenshell.lang.equation;

public class VectorFunction implements IEquationFunction
{
	@Override
	public String getName()
	{
		return "v";
	}

	@Override
	public Val solve(Val x)
	{
		if (x.type == Val.NUMBER || x.type == Val.COMPLEX_NUMBER)
			return new Val(Val.VECTOR, x);

		if (x.type == Val.VECTOR)
			return x;

		if (x.size == 0)
			throw new EquationParserException("Cannot have zero-length vectors!");

		Val[] v = new Val[x.size];

		int firstType = x.get(0).type;
		if (firstType != Val.NUMBER && firstType != Val.COMPLEX_NUMBER)
			throw new EquationParserException("Vectors may only contain numbers or complex numbers!");

		for (int i = 0; i < x.size; i++)
		{
			if (x.get(i).type != firstType)
				throw new EquationParserException("All types within list must match to convert to vector!");
			v[i] = x.get(i);
		}

		return new Val(Val.VECTOR, v);
	}
}
