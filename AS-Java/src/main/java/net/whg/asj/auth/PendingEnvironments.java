package net.whg.asj.auth;

import java.util.ArrayList;
import java.util.List;

public class PendingEnvironments
{
    private final List<EnvironmentFuture> futures = new ArrayList<>();

    void addFuture(EnvironmentFuture future)
    {
        futures.add(future);
    }

    void setFutureStatus(EnvironmentFuture future, boolean success)
    {
        futures.remove(future);

        if (success)
            future.markSuccess();
        else
            future.markFailure();
    }
}
