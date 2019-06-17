package net.whg.awgenshell;

public class CommandParser
{
    public static CommandSet parse(CommandList commandList,
            CommandSender sender, String line)
    {
        CommandSet set = new CommandSet(sender.getVariableKeyring());
        parse(commandList, set, line, sender);
        return set;
    }

    private static CommandVariable parse(CommandList commandList,
            CommandSet set, String line, CommandSender sender)
    {
        TokenTree tree = new TokenTree();
        tree.addTokens(new Tokenizer(line));

        CommandVariable lastVar = null;
        while (tree.hasNextPath())
        {
            TokenPath path = tree.nextPath();
            Token[] tokens = path.getTokens();

            if (tokens.length == 0)
                continue;
            if (tokens.length == 1
                    && tokens[0].getType() == TokenTemplate.SYMBOL)
                continue;

            int commandTokenIndex = 0;

            if (tokens[0].getType() == TokenTemplate.VARIABLE)
                commandTokenIndex = 2;

            if (tokens[commandTokenIndex].getType() != TokenTemplate.STANDARD)
            {
                if (tokens[0].getType() == TokenTemplate.VARIABLE)
                    lastVar = set.getOrCreateVariable(
                            tokens[0].getValue().substring(1));
                else
                    lastVar = set.getOrCreateVariable(
                            String.valueOf(set.getVariableCount()));

                Command command = new Command("set", new CommandArgument[]
                {
                        asCommandArgument(commandList, set,
                                tokens[commandTokenIndex], sender)
                }, sender);
                CommandExecution exe = new CommandExecution(command, lastVar);
                set.insertCommandExecution(exe);

                continue;
            }

            String commandName = tokens[commandTokenIndex].getValue();

            int argCount = 0;
            for (int i = commandTokenIndex + 1; i < tokens.length; i++)
                if (tokens[i].getType() != TokenTemplate.SYMBOL)
                    argCount++;

            CommandArgument[] args = new CommandArgument[argCount];

            int a = 0;
            for (int i = commandTokenIndex + 1; i < tokens.length; i++)
            {
                if (tokens[i].getType() == TokenTemplate.SYMBOL)
                    continue;

                String val = tokens[i].getValue();

                if (tokens[i].getType() == TokenTemplate.NESTED_COMMAND)
                {
                    CommandVariable v2 = parse(commandList, set, val, sender);
                    if (v2 == null)
                        args[a] = new StringArgument("");
                    else
                        args[a] = new VariableArgument(v2);
                }
                else
                    args[a] = asCommandArgument(commandList, set, tokens[i],
                            sender);

                a++;
            }

            if (tokens[0].getType() == TokenTemplate.VARIABLE)
                lastVar = set
                        .getOrCreateVariable(tokens[0].getValue().substring(1));
            else
                lastVar = set.getOrCreateVariable(
                        String.valueOf(set.getVariableCount()));

            Command command = new Command(commandName, args, sender);
            CommandExecution exe = new CommandExecution(command, lastVar);
            set.insertCommandExecution(exe);
        }

        return lastVar;
    }

    private static CommandArgument asCommandArgument(CommandList commandList,
            CommandSet set, Token token, CommandSender sender)
    {
        switch (token.getType())
        {
            case TokenTemplate.STANDARD:
            case TokenTemplate.STRING:
                return new StringArgument(token.getValue());

            case TokenTemplate.VARIABLE:
                return new VariableArgument(
                        set.getOrCreateVariable(token.getValue().substring(1)));

            case TokenTemplate.DYNAMIC_VARIABLE:
                return new DynamicVariableArgument(commandList, sender,
                        token.getValue());

            default:
                throw new CommandParseException(
                        "Unknown token type! " + token.getType());
        }
    }

}
