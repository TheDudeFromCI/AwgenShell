package net.whg.awgenshell.lang.equation;

import java.util.Arrays;
import ch.obermuhlner.math.big.BigFloat;

/**
 * This class is an immutable object which represents a value within the
 * equation solver. This object can be either a single value or a list of
 * values, representing some form of data structure.
 *
 * @author TheDudeFromCI
 */
public class Val
{
	/**
	 * This function adds one value to another. Supports Numbers, Vectors, and
	 * Complex Numbers.
	 *
	 * @param a
	 *     - The first value
	 * @param b
	 *     - The second value.
	 * @return The result of the operation as a new value.
	 */
	public static Val add(Val a, Val b)
	{
		if (a.type != b.type)
		{
			if (a.type == VECTOR && (b.type == COMPLEX_NUMBER || b.type == NUMBER))
				;// ALLOW
			else if (a.type == COMPLEX_NUMBER && b.type == NUMBER)
				b = new Val(COMPLEX_NUMBER, b, Val.ZERO);
			else if (a.type == NUMBER && b.type == COMPLEX_NUMBER)
				a = new Val(COMPLEX_NUMBER, a, Val.ZERO);
			else
				throw new EquationParserException("Cannot add the two given value types!");
		}

		switch (a.type)
		{
			case NUMBER:
				return new Val(a.value.add(b.value));

			case VECTOR:
				if (b.type != VECTOR)
				{
					Val[] elements = new Val[a.size];
					for (int i = 0; i < a.size; i++)
						elements[i] = add(a.get(i), b);

					return new Val(VECTOR, elements);
				}
				else
				{
					if (a.size != b.size)
						throw new EquationParserException("Cannot add vectors of different sizes!");

					Val[] elements = new Val[a.size];
					for (int i = 0; i < a.size; i++)
						elements[i] = add(a.get(i), b.get(i));

					return new Val(VECTOR, elements);
				}

			case COMPLEX_NUMBER:
				Val[] c = new Val[2];

				c[0] = add(a.get(0), b.get(0));
				c[1] = add(a.get(1), b.get(1));

				return new Val(COMPLEX_NUMBER, c);

			default:
				throw new EquationParserException("Cannot add list-type values!");
		}
	}

	/**
	 * This function subtracts one value from another. Supports Numbers, Vectors,
	 * and Complex Numbers.
	 *
	 * @param a
	 *     - The first value
	 * @param b
	 *     - The second value.
	 * @return The result of the operation as a new value.
	 */
	public static Val sub(Val a, Val b)
	{
		if (a.type != b.type)
			if (a.type == VECTOR && b.type == COMPLEX_NUMBER || b.type == NUMBER)
				;// ALLOW
			else if (a.type == COMPLEX_NUMBER && b.type == NUMBER)
				b = new Val(COMPLEX_NUMBER, b, Val.ZERO);
			else if (a.type == NUMBER && b.type == COMPLEX_NUMBER)
				a = new Val(COMPLEX_NUMBER, a, Val.ZERO);
			else
				throw new EquationParserException("Cannot subtract the two given value types!");

		switch (a.type)
		{
			case NUMBER:
				return new Val(a.value.subtract(b.value));

			case VECTOR:
				if (b.type != VECTOR)
				{
					Val[] elements = new Val[a.size];
					for (int i = 0; i < a.size; i++)
						elements[i] = sub(a.get(i), b);

					return new Val(VECTOR, elements);
				}
				else
				{
					if (a.size != b.size)
						throw new EquationParserException("Cannot subtract vectors of different sizes!");

					Val[] elements = new Val[a.size];
					for (int i = 0; i < a.size; i++)
						elements[i] = sub(a.get(i), b.get(i));

					return new Val(VECTOR, elements);
				}

			case COMPLEX_NUMBER:
				Val[] c = new Val[2];

				c[0] = sub(a.get(0), b.get(0));
				c[1] = sub(a.get(1), b.get(1));

				return new Val(COMPLEX_NUMBER, c);

			default:
				throw new EquationParserException("Cannot subtract list-type values!");
		}
	}

	/**
	 * This function multiplies one value with another. Supports Numbers and Complex
	 * Numbers.
	 *
	 * @param a
	 *     - The first value
	 * @param b
	 *     - The second value.
	 * @return The result of the operation as a new value.
	 */
	public static Val mul(Val a, Val b)
	{
		if (a.type != b.type)
			if (a.type == VECTOR && b.type == COMPLEX_NUMBER || b.type == NUMBER)
				;// ALLOW
			else if (a.type == COMPLEX_NUMBER && b.type == NUMBER)
				b = new Val(COMPLEX_NUMBER, b, Val.ZERO);
			else if (a.type == NUMBER && b.type == COMPLEX_NUMBER)
				a = new Val(COMPLEX_NUMBER, a, Val.ZERO);
			else
				throw new EquationParserException("Cannot multiply the two given value types!");

		switch (a.type)
		{
			case NUMBER:
				return new Val(a.value.multiply(b.value));

			case VECTOR:
				if (b.type != VECTOR)
				{
					Val[] elements = new Val[a.size];
					for (int i = 0; i < a.size; i++)
						elements[i] = mul(a.get(i), b);

					return new Val(VECTOR, elements);
				}
				else
				{
					if (a.size != b.size)
						throw new EquationParserException("Cannot multiply vectors of different sizes!");

					Val s = Val.ZERO;
					for (int i = 0; i < a.size; i++)
						s = add(s, mul(a.get(i), b.get(i)));

					return s;
				}

			case COMPLEX_NUMBER:
				Val a1 = a.get(0);
				Val a2 = a.get(1);
				Val b1 = b.get(0);
				Val b2 = b.get(1);

				Val s1 = mul(a1, b1);
				Val s2 = mul(a2, b2);
				Val s3 = sub(s1, s2);

				Val s4 = mul(a1, b2);
				Val s5 = mul(a2, b1);
				Val s6 = add(s4, s5);

				return new Val(COMPLEX_NUMBER, s3, s6);

			default:
				throw new EquationParserException("Cannot multiply list-type values!");
		}
	}

	/**
	 * This function divides one value by another. Supports Numbers and Complex
	 * Numbers.
	 *
	 * @param a
	 *     - The first value
	 * @param b
	 *     - The second value.
	 * @return The result of the operation as a new value.
	 */
	public static Val div(Val a, Val b)
	{
		if (a.type != b.type)
			throw new EquationParserException("Cannot divide the two given value types!");

		switch (a.type)
		{
			case NUMBER:
				return new Val(a.value.divide(b.value));

			case VECTOR:
				throw new EquationParserException("Cannot divide vector values!");

			case COMPLEX_NUMBER:
				Val a1 = a.get(0);
				Val a2 = a.get(1);
				Val b1 = b.get(0);
				Val b2 = b.get(1);
				Val c1 = b1;
				Val c2 = mul(b2, new Val(-1));

				Val t1 = mul(a1, c1);
				Val t2 = mul(a1, c2);
				Val t3 = mul(a2, c1);
				Val t4 = mul(a2, c2);

				Val l1 = mul(b1, c1);
				Val l2 = mul(b1, c2);
				Val l3 = mul(b2, c1);
				Val l4 = mul(b2, c2);

				Val s1 = add(t1, t4);
				Val s2 = add(t2, t3);

				Val s3 = add(l1, l2);
				Val s4 = add(l3, l4);
				Val s5 = add(s3, s4);

				Val o1 = div(s1, s5);
				Val o2 = div(s2, s5);

				return new Val(COMPLEX_NUMBER, o1, o2);

			default:
				throw new EquationParserException("Cannot divide list-type values!");
		}
	}

	/**
	 * This function sets one value to the power of another. Supports Numbers.
	 *
	 * @param a
	 *     - The first value
	 * @param b
	 *     - The second value.
	 * @return The result of the operation as a new value.
	 */
	public static Val pow(Val a, Val b)
	{
		if (a.type != b.type)
			throw new EquationParserException("Cannot power the two given value types!");

		switch (a.type)
		{
			case NUMBER:
				return new Val(a.value.pow(b.value));

			case VECTOR:
				throw new EquationParserException("Cannot power vector values!");

			case COMPLEX_NUMBER:
				throw new EquationParserException("Cannot power complex number values!");

			default:
				throw new EquationParserException("Cannot power list-type values!");
		}
	}

	/**
	 * This function sets one value to the modulus of another. Supports Numbers.
	 *
	 * @param a
	 *     - The first value
	 * @param b
	 *     - The second value.
	 * @return The result of the operation as a new value.
	 */
	public static Val mod(Val a, Val b)
	{
		if (a.type != b.type)
			throw new EquationParserException("Cannot modulus the two given value types!");

		switch (a.type)
		{
			case NUMBER:
				return new Val(a.value.remainder(b.value));

			case VECTOR:
				throw new EquationParserException("Cannot modulus vector values!");

			case COMPLEX_NUMBER:
				throw new EquationParserException("Cannot modulus complex number values!");

			default:
				throw new EquationParserException("Cannot modulus list-type values!");
		}
	}

	/**
	 * This function adds an element to the end of a list value.
	 *
	 * @param list
	 *     - The list.
	 * @param v
	 *     - The value to append.
	 * @return The new, extended list object.
	 */
	public static Val append(Val list, Val v)
	{
		Val[] array = new Val[list.size + 1];

		for (int i = 0; i < list.size; i++)
			array[i] = list.get(i);

		array[list.size] = v;

		return new Val(LIST, array);
	}

	/**
	 * Checks if this value exists within the real number line or not. Numbers will
	 * always return true. For case of complex numbers, this method will return true
	 * only is the imaginary value is equal to zero. For vectors, will return true
	 * if all elements within the vector are real. Lists will always return false.
	 *
	 * @param x
	 *     - The value to check.
	 * @return True if this value is considered real. False otherwise.
	 */
	public static boolean isReal(Val x)
	{
		if (x.type == NUMBER)
			return true;

		if (x.type == COMPLEX_NUMBER)
			return x.get(0).equals(Val.ZERO);

		if (x.type == VECTOR)
		{
			for (Val v : x.elements)
				if (!isReal(v))
					return false;
			return true;
		}

		return false;
	}

	// =================================================================================================================

	/**
	 * A standard rational number type. Element size is always equal to one and
	 * always returns itself. Value returns the float this value represents.
	 */
	public static final int NUMBER = 0;

	/**
	 * A vector. This is a list of numbers which are designed to be manipulated
	 * together as a vector object. Value always returns null.
	 */
	public static final int VECTOR = 1;

	/**
	 * A number which exists on the complex plane. Allows for exactly two elements.
	 * The first element is the real value and the second element is imaginary.
	 */
	public static final int COMPLEX_NUMBER = 2;

	/**
	 * A list of values. Lists can be any length, including 0, and may contain other
	 * lists. Value always returns null.
	 */
	public static final int LIST = 3;

	// =================================================================================================================

	/**
	 * A constant for an empty list value.
	 */
	public static final Val EMPTY_LIST = new Val(LIST, new Val[0]);

	/**
	 * A constant for a number zero value.
	 */
	public static final Val ZERO = new Val(0);

	/**
	 * A constant for a number one value.
	 */
	public static final Val ONE = new Val(1);

	/**
	 * A constant for a number negative one value.
	 */
	public static final Val NEGATIVE_ONE = new Val(-1);

	/**
	 * A constant for a complex number zero.
	 */
	public static final Val ZERO_COMPLEX = new Val(COMPLEX_NUMBER, ZERO, ZERO);

	/**
	 * A constant for a complex number one.
	 */
	public static final Val ONE_COMPLEX = new Val(COMPLEX_NUMBER, ONE, ZERO);

	/**
	 * A constant for a complex number i.
	 */
	public static final Val I_COMPLEX = new Val(COMPLEX_NUMBER, ZERO, ONE);

	/**
	 * A constant for the number pi
	 */
	public static final Val PI = new Val(3.1415926535897932384626433832795028841971693993751058209749445923078);

	/**
	 * A constant for the numer E.
	 */
	public static final Val E = new Val(2.7182818284590452353602874713526624977572470936999595749669676277240);

	// =================================================================================================================

	/**
	 * The array of elements which is stored within this object. Kepyt private to
	 * ensure this object stays read-only.
	 */
	private final Val[] elements;

	/**
	 * Gets the actual value of this object as a BigFloat. This method returns null
	 * if this object is not a Number type.
	 *
	 * @return The BigFloat value of this object.
	 */
	public final BigFloat value;

	/**
	 * Gets the type of this object.
	 *
	 * @return The type.
	 */
	public final int type;

	/**
	 * Gets the number of elements within this object.
	 *
	 * @return The size.
	 */
	public final int size;

	/**
	 * Creates a new number value.
	 *
	 * @param value
	 *     - The string to parse into a number value.
	 */
	public Val(String value)
	{
		this(BigFloat.context(128).valueOf(value));
	}

	/**
	 * Creates a new number value.
	 *
	 * @param value
	 *     - The value as a long.
	 */
	public Val(long value)
	{
		this(BigFloat.context(128).valueOf(value));
	}

	/**
	 * Creates a new number value.
	 *
	 * @param value
	 *     - The value as a double.
	 */
	public Val(double value)
	{
		this(BigFloat.context(128).valueOf(value));
	}

	/**
	 * Creates a new number value.
	 *
	 * @param value
	 *     - The value as a BigFloat.
	 */
	public Val(BigFloat value)
	{
		this(new Val[1], value, NUMBER);
		elements[0] = this;
	}

	/**
	 * Creates a new multi-element value, with the given type. The types may be one
	 * of Complex Number, Vector, or List.
	 *
	 * @param elements
	 *     - An array of elements to store within this value.
	 * @param type
	 *     - The type of value this value represents.
	 */
	public Val(int type, Val... elements)
	{
		this(elements, null, type);
	}

	/**
	 * The main constructor for this class. Acts as a sanity filter for specifying
	 * object types.
	 *
	 * @param elements
	 *     - The elements within this object.
	 * @param value
	 *     - The value as a BigFloat.
	 * @param type
	 *     - The type of value this value represents.
	 */
	private Val(Val[] elements, BigFloat value, int type)
	{
		this.elements = elements;
		this.value = value;
		this.type = type;
		size = elements.length;

		switch (type)
		{
			case NUMBER:
				if (elements.length != 1)
					throw new EquationParserException("Numbers may only contain one element!");

				if (value == null)
					throw new EquationParserException("Numbers may not contain a null value!");
				break;

			case VECTOR:
				if (elements.length <= 0)
					throw new EquationParserException("Vectors must contain at least one element!");

				if (value != null)
					throw new EquationParserException("Vectors cannot have a value!");

				boolean containsComplex = false;
				boolean containsReal = false;
				for (Val v : elements)
					if (v.type == COMPLEX_NUMBER)
						containsComplex = true;
					else if (v.type == NUMBER)
						containsReal = true;
					else
						throw new EquationParserException("Vectors may only contain numbers or complex numbers!");

				if (containsComplex && containsReal) // Convert real numbers to complex numbers
					for (int i = 0; i < size; i++)
						if (elements[i].type == NUMBER)
							elements[i] = new Val(COMPLEX_NUMBER, elements[i], Val.ZERO);

				break;

			case COMPLEX_NUMBER:
				if (elements.length != 2)
					throw new EquationParserException("Complex numbers must contain exactly two elements!");

				if (value != null)
					throw new EquationParserException("Complex numbers cannot have a value!");

				for (Val v : elements)
					if (v.type != NUMBER)
						throw new EquationParserException("Complex numbers may only contain numbers!");
				break;

			case LIST:
				if (value != null)
					throw new EquationParserException("Lists cannot have a value!");
				break;

			default:
				throw new EquationParserException("Not a valid value type!");
		}
	}

	/**
	 * Gets an element within this object based on the given index. If this object
	 * is a Number, getting element 0 will return this object.
	 *
	 * @param index
	 *     - The index of the element.
	 * @return The Val object at that element index.
	 */
	public Val get(int index)
	{
		return elements[index];
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(elements);
		result = prime * result + size;
		result = prime * result + type;
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Val other = (Val) obj;
		if (type != other.type)
		{
			if (type == NUMBER && other.type == COMPLEX_NUMBER)
			{
				if (!other.get(1).equals(Val.ZERO))
					return false;

				if (!get(0).equals(other.get(0)))
					return false;
			}
			else if (type == COMPLEX_NUMBER && other.type == NUMBER)
			{
				if (!get(1).equals(Val.ZERO))
					return false;

				if (!get(0).equals(other.get(0)))
					return false;
			}
			else
				return false;
		}

		if (type == VECTOR || type == LIST)
		{
			if (!Arrays.equals(elements, other.elements))
				return false;
		}

		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!value.isEqual(other.value))
			return false;

		return true;
	}

	@Override
	public String toString()
	{
		switch (type)
		{
			case NUMBER:
			{
				StringBuilder sb = new StringBuilder(value.toString());

				if (sb.length() >= 3 && sb.indexOf(".0") == sb.length() - 2)
					sb.delete(sb.length() - 2, sb.length());

				int dec = sb.indexOf(".");
				if (dec == -1)
					dec = sb.length();

				for (int i = dec - 3; i > 0; i -= 3)
					sb.insert(i, ',');

				return sb.toString();
			}

			case VECTOR:
			{
				StringBuilder sb = new StringBuilder();
				sb.append('(');

				for (int i = 0; i < size; i++)
				{
					if (i > 0)
						sb.append(", ");

					sb.append(get(i));
				}

				sb.append(')');
				return sb.toString();
			}

			case COMPLEX_NUMBER:
			{
				if (get(0).value.isZero() && get(1).value.isZero())
					return "0";

				StringBuilder sb = new StringBuilder();

				if (!get(0).value.isZero())
					sb.append(get(0));

				if (!get(1).value.isZero())
				{
					if (sb.length() != 0)
						sb.append(" + ");

					if (!get(1).equals(Val.ONE))
						sb.append(get(1));
					sb.append('i');
				}

				if (sb.length() == 0)
					sb.append("0");

				return sb.toString();
			}

			case LIST:
			{
				StringBuilder sb = new StringBuilder();
				sb.append('[');

				for (int i = 0; i < size; i++)
				{
					if (i > 0)
						sb.append(", ");

					sb.append(get(i));
				}

				sb.append(']');
				return sb.toString();
			}

			default:
				return "0";
		}
	}
}
