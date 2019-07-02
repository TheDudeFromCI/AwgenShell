package net.whg.awgenshell;

/**
 * This class represents an immutable single permission node which an indvidual
 * can have. A permission node, represented by a string of alphanumerical words
 * seperated by periods, which represent a permission capability from the owner.
 * This is used to validate a single, specific permission operation.
 *
 * @author TheDudeFromCI
 */
public class PermissionNode
{
	private final String raw;
	private final String[] elements;
	private final boolean isLiteral;

	/**
	 * Creates a new permission node from the given string. A permission node
	 * follows the format: <br>
	 * <br>
	 * <code>
	 * abc.123.do-re-mi
	 * </code><br>
	 * <br>
	 * where the node consists of a series of 'words' seperated by periods, where a
	 * word may consist of letters, numbers, dashes, or underscores. Each word is a
	 * subcategory of the previous word. As such, do-re-mi is a child of the 123
	 * category, which is in turn a child of the abc category. An asterisk can be
	 * used in place of a word, to represent "true for all possible inputs" at that
	 * location. If the asterisk is the final word in a the sequence, then all
	 * children of that word are included and considered true as well.
	 *
	 * @param node
	 */
	public PermissionNode(String node)
	{
		validate();

		raw = node;
		elements = node.split("\\.");
		isLiteral = !node.contains("*");
	}

	private void validate()
	{
		if (!raw.matches("([A-Za-z0-9_-]+|*)(\\.[A-Za-z0-9_-]+|*)*"))
			throw new IllegalArgumentException("Not a valid permission node!");
	}

	/**
	 * Checks if this permission node is literal. A permission node is considered
	 * literal if it contains no asterisks.
	 *
	 * @return True if this permission is literal, false otherwise.
	 */
	public boolean isLiteral()
	{
		return isLiteral;
	}

	/**
	 * Checks if this permission node encapsulates the permission requirements of
	 * another node. Encapsulation is considered true if either both permssion nodes
	 * are exact matches, or if this permission node contains wildcards, covering
	 * the correct words. <br>
	 * <br>
	 * If the given permission node is not literal, then this function will always
	 * return false;
	 *
	 * @param other
	 *     - The permssion node to check against. Must be literal.
	 * @return True if this permssion node encapsulates the given permission node.
	 */
	public boolean matches(PermissionNode other)
	{
		if (!other.isLiteral())
			return false;

		for (int i = 0; i < elements.length; i++)
		{
			if (elements[i].equals("*"))
			{
				if (i == elements.length - 1)
					return true;
			}
			else
			{
				if (!elements[i].equals(other.elements[i]))
					return false;
			}
		}

		if (elements.length != other.elements.length)
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		return raw.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof PermissionNode)
			return raw.equals(((PermissionNode) obj).raw);
		return false;
	}
}
