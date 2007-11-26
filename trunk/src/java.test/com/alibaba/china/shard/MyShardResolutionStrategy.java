package com.alibaba.china.shard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategy;
import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategyData;

public class MyShardResolutionStrategy implements ShardResolutionStrategy {
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
        if (shardResolutionStrategyData instanceof LoginNameResolutionStrategyData) {
            LoginNameResolutionStrategyData data = (LoginNameResolutionStrategyData) shardResolutionStrategyData;
            Set<ShardId> set = new HashSet<ShardId>(data.getData().size());
            for (String name : data.getData()) {
                int mod = Math.abs(name.hashCode() % this.allShardCount);
                if (logger.isDebugEnabled()){
                    logger.debug(String.format("%s assigned to shardId: %d", name,mod));
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
