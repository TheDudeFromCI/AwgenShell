package net.whg.awgenshell;

/**
 * Usered to determine the coverage level of permission nodes when validating
 * permissions.
 *
 * @author TheDudeFromCI
 */
public enum PermissionType
{
	/**
	 * When checking the permission coverage of a permission node, if the response
	 * is a whitelist, the given permission is considered valid by the permissio
	 * node. This reponse may be overriden by a blacklist from other permission
	 * nodes within the group.
	 */
	WHITELIST,

	/**
	 * When checking the permission coverage of a permission node, a neutral
	 * response indicates that the permission node does not target the given
	 * permission in any way.
	 */
	NEUTRAL,

	/**
	 * When checking the permission coverage of a permission node, a blacklist
	 * response indicates that the given permission has explicitly denied by the
	 * permission node.
	 */
	BLACKLIST;

	/**
	 * Runs an or operation with this permission type and another permission type.
	 * This allows multiple permissions to be chained together and quickly determine
	 * the reponse. A permission type of whitelist will always be prioritized over a
	 * permission type of neutral. Likewise, a permission type of blacklist will
	 * always be prioritized over whitelist.
	 * 
	 * @param other
	 *     - The permission type to compare against.
	 * @return The resulting permission type.
	 */
	public PermissionType or(PermissionType other)
	{
		switch (this)
		{
			case WHITELIST:
				if (other == PermissionType.BLACKLIST)
					return other;
				return this;

			case BLACKLIST:
				return this;

			case NEUTRAL:
				return other;

			default:
				throw new RuntimeException();

		}
	}
}
