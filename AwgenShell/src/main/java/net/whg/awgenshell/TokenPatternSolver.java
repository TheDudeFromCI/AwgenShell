package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

public class TokenPatternSolver
{
    public static TokenPatternSolver[] compile(String pattern)
    {
        List<TokenPatternSolver> solvers = new ArrayList<>();

        for (int i = 0; i < pattern.length(); i++)
        {
            char type = pattern.charAt(i);
            char quan = '1';

            if (i < pattern.length() - 1)
            {
                char after = pattern.charAt(i + 1);
                if (after == '*' || after == '+' || after == '?')
                {
                    quan = after;
                    i++;
                }
            }

            solvers.add(new TokenPatternSolver(type, quan));
        }

        return solvers.toArray(new TokenPatternSolver[solvers.size()]);
    }

    private char _type;
    private char _quantifier;

    public TokenPatternSolver(char type, char quantifier)
    {
        _type = type;
        _quantifier = quantifier;
    }

    public char getType()
    {
        return _type;
    }

    public char getQuantifier()
    {
        return _quantifier;
    }

    public boolean matches(Token token)
    {
        switch (_type)
        {
            case 'a':
                return token.getType() == TokenTemplate.STANDARD;

            case 'v':
                return token.getType() == TokenTemplate.VARIABLE;

            case 'n':
                return token.getType() == TokenTemplate.NESTED_COMMAND;

            case 's':
                return token.getType() == TokenTemplate.STRING;

            case 'd':
                return token.getType() == TokenTemplate.DYNAMIC_VARIABLE;

            case 'p':
                return token.getType() == TokenTemplate.STANDARD
                        || token.getType() == TokenTemplate.STRING
                        || token.getType() == TokenTemplate.VARIABLE
                        || token.getType() == TokenTemplate.NESTED_COMMAND
                        || token.getType() == TokenTemplate.DYNAMIC_VARIABLE
                        || token.getType() == TokenTemplate.SYMBOL
                                && token.getValue().equals(",");

            case '=':
                return token.getType() == TokenTemplate.SYMBOL
                        && token.getValue().equals("=");

            case ';':
                return token.getType() == TokenTemplate.SYMBOL
                        && token.getValue().equals(";");

            default:
                throw new IllegalStateException("Unknown pattern type!");
        }
    }

    public boolean isGreedy()
    {
        return _quantifier == '*' || _quantifier == '+';
    }

    public boolean isOptional()
    {
        return _quantifier == '?' || _quantifier == '*';
    }

    public int count(List<Token> tokens, int index)
    {
        int count = 0;

        for (int i = index; i < tokens.size(); i++)
        {
            if (matches(tokens.get(i)))
            {
                count++;

                if (!isGreedy())
                    break;
            }
            else
                break;
        }

        return count;
    }
}
