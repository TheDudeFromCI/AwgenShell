package net.whg.asj.auth;

public class AuthenticationFailureException extends RuntimeException
{

    private static final long serialVersionUID = 9308421751L;

    public AuthenticationFailureException(String message)
    {
        super(message);
    }
}
