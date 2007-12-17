package org.isharding.shard.strategy.resolution;

import java.util.List;

import org.isharding.shard.ShardId;

/**
 * 
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public interface ShardResolutionStrategy {
    /**
     * 检测对象可能存在的shard
     *
     * @param shardResolutionStrategyData 用来判断所属shard的一些数据
     * @return 数据存放的shard ids
     */
    List<ShardId> selectShardIdsFromShardResolutionStrategyData(
        ShardResolutionStrategyData shardResolutionStrategyData);
  }