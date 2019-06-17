package net.whg.awgenshell;

import java.util.List;

public class TokenTreePattern
{
    private TokenPatternSolver[] _solvers;

    public TokenTreePattern(String pattern)
    {
        _solvers = TokenPatternSolver.compile(pattern);
    }

    public TokenPath build(List<Token> tokens)
    {
        int tokenIndex = 0;

        for (int i = 0; i < _solvers.length; i++)
        {
            int count = _solvers[i].count(tokens, tokenIndex);

            if (count == 0 && !_solvers[i].isOptional())
                return null;

            tokenIndex += count;
        }

        Token[] tokenArray = new Token[tokenIndex];
        for (int i = 0; i < tokenArray.length; i++)
            tokenArray[i] = tokens.remove(0);

        return new TokenPath(tokenArray);
    }
}
