package com.alibaba.china.shard.strategy;

import com.alibaba.china.shard.strategy.access.ShardAccessStrategy;
import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategy;
import com.alibaba.china.shard.strategy.selection.ShardSelectionStrategy;

public interface ShardStrategy {

    ShardSelectionStrategy getShardSelectionStrategy();

    ShardResolutionStrategy getShardResolutionStrategy();

    ShardAccessStrategy getShardAccessStrategy();
}
