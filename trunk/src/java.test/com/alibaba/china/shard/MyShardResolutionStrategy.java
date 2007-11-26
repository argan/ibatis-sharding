package com.alibaba.china.shard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategy;
import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategyData;

public class MyShardResolutionStrategy implements ShardResolutionStrategy {
    private int allShardCount = 1024;

    public void setAllShardCount(int allShardCount) {
        if (allShardCount < 0) {
            throw new IllegalArgumentException("allShardCount must greater than 0(zero)");
        }
        this.allShardCount = allShardCount;
    }

    public List<ShardId> selectShardIdsFromShardResolutionStrategyData(
            ShardResolutionStrategyData shardResolutionStrategyData) {
        if (shardResolutionStrategyData instanceof LoginNameResolutionStrategyData) {
            LoginNameResolutionStrategyData data = (LoginNameResolutionStrategyData) shardResolutionStrategyData;
            Set<ShardId> set = new HashSet<ShardId>(data.getData().size());
            for (String name : data.getData()) {
                ShardId shardId = new ShardId(Math.abs(name.hashCode() % this.allShardCount));
                if (set.contains(shardId) == false) {
                    set.add(shardId);
                }
            }
            return new ArrayList<ShardId>(set);
        }
        return null;
    }

}
