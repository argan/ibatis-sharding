package org.isharding.shard.strategy.access.exit;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

/**
 * 吗也不做的退出策略
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class NoopExitStrategy  implements ExitStrategy  {

    public boolean addResult(Object result, Shard shard) {
        return false;
    }

    public Object compileResults(ExitOperationsCollector exitOperationsCollector) {
        return null;
    }

}
