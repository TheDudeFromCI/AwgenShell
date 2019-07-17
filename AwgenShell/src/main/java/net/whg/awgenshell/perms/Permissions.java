package net.whg.awgenshell.perms;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of permissions that is held by a user.
 *
 * @author TheDudeFromCI
 */
public class Permissions
{
	/**
	 * A simple static implementation of permissions which encapsulates all
	 * permissions.
	 */
	public static final Permissions ALL;

	/**
	 * A simple static implementation of permissions which encapsulates only
	 * language permissions, and nothing else.
	 */
	public static final Permissions LANG;

	static
	{
		ALL = new Permissions();
		ALL.addPermissionNode(new PermissionNode("*"));

		LANG = new Permissions();
		LANG.addPermissionNode(new PermissionNode("lang.*"));
	}

	private Permissions[] inheirated;
	private List<PermissionNode> permissionNodes = new ArrayList<>();

	/**
	 * Creates a new permissions collection.
	 *
	 * @param inheirated
	 *     - Allows this permissions object to inheirate permissions dynamically
	 *     from other permissions groups. This is a live connection, and any future
	 *     nodes add to those groups are automatically inheirated by this group.
	 */
	public Permissions(Permissions... inheirated)
	{
		this.inheirated = inheirated;
	}

	/**
	 * Adds a new permission node to this permission collection. Does nothing if
	 * input is null or is already in the list.
	 *
	 * @param node
	 *     - The permission node to add.
	 */
	public void addPermissionNode(PermissionNode node)
	{
		if (node == null)
			return;

		if (permissionNodes.contains(node))
			return;

		permissionNodes.add(node);
	}

	/**
	 * Removes a permission node from this permission collection. Does nothing if
	 * input is null.
	 *
	 * @param node
	 *     - The permission node to remove.
	 */
	public void removePermissionNode(PermissionNode node)
	{
		if (node == null)
			return;

		permissionNodes.remove(node);
	}

	/**
	 * Gets thie number of permission nodes in this collection. Does not count
	 * inhierated permissions.
	 *
	 * @return The number of permission nodes in this collection/
	 */
	public int getCount()
	{
		return permissionNodes.size();
	}

	/**
	 * Gets the permission node at the specified index within this collection.
	 *
	 * @param index
	 *     - The index of the permission node.
	 * @return The permission node.
	 */
	public PermissionNode getIndex(int index)
	{
		return permissionNodes.get(index);
	}

	/**
	 * Check if this permission node has any permissions which encapsulate the given
	 * node, checking local and inheirated permissions as needed. Function will
	 * always return false if input is not a literal node.
	 *
	 * @param node
	 *     - The permission to check for. Must be a literal.
	 * @return True if this permissions collection encapsules the given permission,
	 *     false otherwise.
	 */
	public boolean hasPermission(PermissionNode node)
	{
		if (!node.isLiteral())
			return false;

		PermissionType perm = PermissionType.NEUTRAL;

		for (PermissionNode s : permissionNodes)
			perm = perm.or(s.matches(node));

		for (Permissions p : inheirated)
			for (PermissionNode s : p.permissionNodes)
				perm = perm.or(s.matches(node));

		return perm == PermissionType.WHITELIST;
	}
}
