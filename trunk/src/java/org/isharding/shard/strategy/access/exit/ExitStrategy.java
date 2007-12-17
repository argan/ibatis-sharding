package org.isharding.shard.strategy.access.exit;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

/**
 * 退出策略，如果在多个shards中执行，可能需要中途退出操作
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public interface ExitStrategy {

    boolean addResult(Object result, Shard shard);

    Object compileResults(ExitOperationsCollector exitOperationsCollector);
}
