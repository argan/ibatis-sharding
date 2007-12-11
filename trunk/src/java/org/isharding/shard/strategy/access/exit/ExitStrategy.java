package org.isharding.shard.strategy.access.exit;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

public interface ExitStrategy {

    boolean addResult(Object result, Shard shard);

    Object compileResults(ExitOperationsCollector exitOperationsCollector);
}
