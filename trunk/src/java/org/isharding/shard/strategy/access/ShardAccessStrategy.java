package org.isharding.shard.strategy.access;

import java.util.List;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.exit.ExitStrategy;

public interface ShardAccessStrategy {
    Object apply(List<Shard> shards, ShardOperation operation, ExitStrategy exitStrategy, ExitOperationsCollector exitOperationsCollector);
}
