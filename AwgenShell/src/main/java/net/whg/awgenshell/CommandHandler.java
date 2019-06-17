package net.whg.awgenshell;

public interface CommandHandler
{
    /*
     * Gets the core name of this comand that should be typed in the console.
     */
    String getCommandName();

    /**
     * Gets any additional names for this command. These can be used in the case
     * of name confliction or for shorthand purposes.
     *
     * @return An array of additional aliases for this command.
     */
    String[] getCommandAliases();

    /**
     * Gets the description text for this command.
     * 
     * @return The description text for this command.
     */
    String getDescription();

    /**
     * Gets the help text for this command.
     * 
     * @return - The help text for this command.
     */
    String getHelpText();

    /**
     * Executes this command instance.
     *
     * @param command
     *                    - The command instance to execute.
     * @return The output of the command, or null if there isn't one.
     */
    String executeCommand(Command command);
}
