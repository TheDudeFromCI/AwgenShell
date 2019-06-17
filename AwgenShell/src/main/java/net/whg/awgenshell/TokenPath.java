package net.whg.awgenshell;

public class TokenPath
{
    private Token[] _tokens;

    public TokenPath(Token[] tokens)
    {
        _tokens = tokens;
    }

    public Token[] getTokens()
    {
        return _tokens;
    }
}
