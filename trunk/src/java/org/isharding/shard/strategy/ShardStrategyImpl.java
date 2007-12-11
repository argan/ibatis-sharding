package org.isharding.shard.strategy;

import org.isharding.shard.strategy.access.ShardAccessStrategy;
import org.isharding.shard.strategy.resolution.ShardResolutionStrategy;
import org.isharding.shard.strategy.selection.ShardSelectionStrategy;

/**
 * @author maxr@google.com (Max Ross)
 */
public class ShardStrategyImpl implements ShardStrategy {

    private ShardSelectionStrategy  shardSelectionStrategy;
    private ShardResolutionStrategy shardResolutionStrategy;
    private ShardAccessStrategy     shardAccessStrategy;

    public ShardStrategyImpl() {

    }

    public ShardStrategyImpl(ShardSelectionStrategy shardSelectionStrategy,
            ShardResolutionStrategy shardResolutionStrategy, ShardAccessStrategy shardAccessStrategy) {
        this.shardSelectionStrategy = shardSelectionStrategy;
        this.shardResolutionStrategy = shardResolutionStrategy;
        this.shardAccessStrategy = shardAccessStrategy;
    }

    public ShardSelectionStrategy getShardSelectionStrategy() {
        return shardSelectionStrategy;
    }

    public ShardResolutionStrategy getShardResolutionStrategy() {
        return shardResolutionStrategy;
    }

    public ShardAccessStrategy getShardAccessStrategy() {
        return shardAccessStrategy;
    }

    public void setShardSelectionStrategy(ShardSelectionStrategy shardSelectionStrategy) {
        this.shardSelectionStrategy = shardSelectionStrategy;
    }

    public void setShardResolutionStrategy(ShardResolutionStrategy shardResolutionStrategy) {
        this.shardResolutionStrategy = shardResolutionStrategy;
    }

    public void setShardAccessStrategy(ShardAccessStrategy shardAccessStrategy) {
        this.shardAccessStrategy = shardAccessStrategy;
    }
}
