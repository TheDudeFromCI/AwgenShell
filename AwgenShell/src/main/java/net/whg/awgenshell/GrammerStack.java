package net.whg.awgenshell;

public interface GrammerStack
{
	/**
	 * This command tries to create this layer in the grammer stack by consuming as
	 * many tokens as this node can. When no more tokens can be consumed, this layer
	 * is popped from the stack.
	 *
	 * @param tokenizer
	 *     - The tokenizer to pull tokens from.
	 * @return True if any tokens were consumed, false otherwise.
	 */
	boolean consumeTokens(ShellEnvironment env, Tokenizer tokenizer);
}
