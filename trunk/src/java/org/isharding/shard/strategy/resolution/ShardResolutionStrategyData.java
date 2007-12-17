package org.isharding.shard.strategy.resolution;

/**
 * 提供给执行策略选择shards的元数据，一般需要自行实现
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public interface ShardResolutionStrategyData {
    Object getData();
}
