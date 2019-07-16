package permissions;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import net.whg.awgenshell.PermissionNode;
import net.whg.awgenshell.PermissionType;

public class PermissionNodeTest
{
	@Test
	public void singleElement_literal_pass()
	{
		PermissionNode node1 = new PermissionNode("my_perm");
		PermissionNode node2 = new PermissionNode("my_perm");

		assertEquals(PermissionType.WHITELIST, node1.matches(node2));
	}

	@Test
	public void multipleElements_literal_pass()
	{
		PermissionNode node1 = new PermissionNode("a.b.c");
		PermissionNode node2 = new PermissionNode("a.b.c");

		assertEquals(PermissionType.WHITELIST, node1.matches(node2));
	}

	@Test
	public void notLiteral_wildcard_fail()
	{
		PermissionNode node1 = new PermissionNode("literal");
		PermissionNode node2 = new PermissionNode("*");

		assertEquals(PermissionType.NEUTRAL, node1.matches(node2));
	}

	@Test
	public void notLiteral_blacklist_fail()
	{
		PermissionNode node1 = new PermissionNode("literal");
		PermissionNode node2 = new PermissionNode("!literal");

		assertEquals(PermissionType.NEUTRAL, node1.matches(node2));
	}

	@Test
	public void lastElementWildcard_pass()
	{
		PermissionNode node1 = new PermissionNode("a.*");
		PermissionNode node2 = new PermissionNode("a.b.c");

		assertEquals(PermissionType.WHITELIST, node1.matches(node2));
	}

	@Test
	public void different_patterns_fail()
	{
		PermissionNode node1 = new PermissionNode("a.b.c");
		PermissionNode node2 = new PermissionNode("1.2.3");

		assertEquals(PermissionType.NEUTRAL, node1.matches(node2));
	}

	@Test
	public void fullWildcard_pass()
	{
		PermissionNode node1 = new PermissionNode("*");
		PermissionNode node2 = new PermissionNode("a.b.c");

		assertEquals(PermissionType.WHITELIST, node1.matches(node2));
	}

	@Test
	public void permsMatch_Blacklist_fail()
	{
		PermissionNode node1 = new PermissionNode("!a.b.c");
		PermissionNode node2 = new PermissionNode("a.b.c");

		assertEquals(PermissionType.BLACKLIST, node1.matches(node2));
	}

	@Test
	public void permsDontMatch_Blacklist_fail()
	{
		PermissionNode node1 = new PermissionNode("!a.b.c");
		PermissionNode node2 = new PermissionNode("1.2.3");

		assertEquals(PermissionType.NEUTRAL, node1.matches(node2));
	}
}
