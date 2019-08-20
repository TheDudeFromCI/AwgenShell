package commands;

import static util.Checks.check;
import org.junit.Test;

public class IfTest
{
	@Test
	public void commands()
	{
		check("if true then { print red }", "red");
		check("if true then { print red } else { print blue }", "red");
		check("if false then { print red } else { print blue }", "blue");
		check("if false then { print red } elseif false then { print blue } else { print green }", "green");
		check("if false then { print red } elseif true then { print blue } else { print green }", "blue");
		check("if false then { print red } elseif true then { print blue } elseif true then { print purple } else { print green }",
				"blue");
	}
}
