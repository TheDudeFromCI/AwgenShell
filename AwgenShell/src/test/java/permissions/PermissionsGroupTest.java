package permissions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import net.whg.awgenshell.PermissionNode;
import net.whg.awgenshell.Permissions;

public class PermissionsGroupTest
{
	@Test
	public void singlePermission_Matches_pass()
	{
		Permissions perms = new Permissions();
		perms.addPermissionNode(new PermissionNode("a.b.c"));

		assertTrue(perms.hasPermission(new PermissionNode("a.b.c")));
	}

	@Test
	public void singlePermission_DoesntMatch_fail()
	{
		Permissions perms = new Permissions();
		perms.addPermissionNode(new PermissionNode("a.b.c"));

		assertFalse(perms.hasPermission(new PermissionNode("1.2.3")));
	}

	@Test
	public void multiplePermissions_Contains_pass()
	{
		Permissions perms = new Permissions();
		perms.addPermissionNode(new PermissionNode("a.b.c"));
		perms.addPermissionNode(new PermissionNode("1.2.3"));
		perms.addPermissionNode(new PermissionNode("do.re.mi"));

		assertTrue(perms.hasPermission(new PermissionNode("1.2.3")));
	}

	@Test
	public void multiplePermissions_DoesntContain_fail()
	{
		Permissions perms = new Permissions();
		perms.addPermissionNode(new PermissionNode("a.b.c"));
		perms.addPermissionNode(new PermissionNode("1.2.3"));
		perms.addPermissionNode(new PermissionNode("do.re.mi"));

		assertFalse(perms.hasPermission(new PermissionNode("red.green.blue")));
	}

	@Test
	public void multiplePermissions_Wildcard_pass()
	{
		Permissions perms = new Permissions();
		perms.addPermissionNode(new PermissionNode("red.*"));
		perms.addPermissionNode(new PermissionNode("1.2.3"));
		perms.addPermissionNode(new PermissionNode("do.re.mi"));

		assertTrue(perms.hasPermission(new PermissionNode("red.green.blue")));
	}

	@Test
	public void multiplePermissions_WildcardBlacklist_false()
	{
		Permissions perms = new Permissions();
		perms.addPermissionNode(new PermissionNode("red.*"));
		perms.addPermissionNode(new PermissionNode("!red.green.*"));

		assertFalse(perms.hasPermission(new PermissionNode("red.green.blue")));
	}
}
