package com.alibaba.china.shard.strategy.resolution.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.china.shard.ShardId;
import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategy;
import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategyData;


public class SimpleHashShardResolutionStrategy implements ShardResolutionStrategy {
    private final Log logger        = LogFactory.getLog(getClass());
    private int       allShardCount = 1024;

    public void setAllShardCount(int allShardCount) {
        if (allShardCount < 0) {
            throw new IllegalArgumentException("allShardCount must greater than 0(zero)");
        }
        this.allShardCount = allShardCount;
    }

    public List<ShardId> selectShardIdsFromShardResolutionStrategyData(
            ShardResolutionStrategyData shardResolutionStrategyData) {
        if (shardResolutionStrategyData instanceof SimpleResolutionStrategyData) {
            SimpleResolutionStrategyData data = (SimpleResolutionStrategyData) shardResolutionStrategyData;
            Set<ShardId> set = new HashSet<ShardId>(data.getData().size());
            for (Object o : data.getData()) {
                int mod = Math.abs(o.hashCode() % this.allShardCount);
                if (logger.isDebugEnabled()){
                    logger.debug(String.format("%s assigned to shardId: %d", o,mod));
                }
                ShardId shardId = new ShardId(mod);
                if (set.contains(shardId) == false) {
                    set.add(shardId);
                }
            }
            return new ArrayList<ShardId>(set);
        }
        return null;
    }

}
