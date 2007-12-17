package org.isharding.shard.strategy.access;

import java.util.List;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.exit.ExitStrategy;

/**
 * 访问策略，请求流程的实现，如：并行，串行，或者是结果处理等
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public interface ShardAccessStrategy {
    Object apply(List<Shard> shards, ShardOperation operation, ExitStrategy exitStrategy, ExitOperationsCollector exitOperationsCollector);
}
