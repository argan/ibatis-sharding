package org.isharding.shard.strategy;

import org.isharding.shard.strategy.access.ShardAccessStrategy;
import org.isharding.shard.strategy.resolution.ShardResolutionStrategy;
import org.isharding.shard.strategy.selection.ShardSelectionStrategy;

public interface ShardStrategy {

    ShardSelectionStrategy getShardSelectionStrategy();

    ShardResolutionStrategy getShardResolutionStrategy();

    ShardAccessStrategy getShardAccessStrategy();
}
