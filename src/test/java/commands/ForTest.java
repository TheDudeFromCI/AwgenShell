package commands;

import static util.Checks.check;
import org.junit.Test;

public class ForTest
{
	@Test(timeout = 20000)
	public void simpleCounter()
	{
		check("for $i, 1, 5, 1 do { print $i }", "1", "2", "3", "4", "5");
		check("$j = set 3; for $i, 1, $j do { print $i }", "1", "2", "3");
		check("for $i, 1, 3, 1 do { for $j, 1, 3, 1 do { print $i $j } }", "1 1", "1 2", "1 3", "2 1", "2 2", "2 3",
				"3 1", "3 2", "3 3");
	}
}
