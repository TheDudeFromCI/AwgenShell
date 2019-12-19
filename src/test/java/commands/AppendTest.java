package commands;

import static util.Checks.check;
import org.junit.Test;

public class AppendTest
{
	@Test
	public void emptyList()
	{
		check("append", "");
	}

	@Test
	public void severalElements()
	{
		check("append a b c", "abc");
	}

	@Test
	public void newLineSeperator()
	{
		check("append -n red green blue", "red\ngreen\nblue");
	}

	@Test
	public void customSeperator()
	{
		check("append -s ', ' apples oranges", "apples, oranges");
	}
}
