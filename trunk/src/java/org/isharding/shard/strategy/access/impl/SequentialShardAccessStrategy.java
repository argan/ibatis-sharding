package org.isharding.shard.strategy.access.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;
import org.isharding.shard.strategy.access.ShardAccessStrategy;
import org.isharding.shard.strategy.access.ShardOperation;
import org.isharding.shard.strategy.access.exit.ExitStrategy;

public class SequentialShardAccessStrategy implements ShardAccessStrategy {

    private final Log log = LogFactory.getLog(getClass());

    public Object apply(List<Shard> shards, ShardOperation operation, ExitStrategy exitStrategy,
            ExitOperationsCollector exitOperationsCollector) {
        for (Shard shard : shards) {
            if (exitStrategy.addResult(operation.execute(shard), shard)) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Short-circuiting operation %s after execution against shard %s", operation
                            .getOperationName(), shard));
                }
                break;
            }
        }
        return exitStrategy.compileResults(exitOperationsCollector);
    }

    public Object apply(List<Shard> shards, ShardOperation operation, ExitStrategy exitStrategy) {
        if (log.isDebugEnabled()){
            log.debug(String.format("Executing %s on %d shards.",operation.getOperationName(),shards.size()));
        }
        return this.apply(shards, operation, exitStrategy, null);
    }

}
