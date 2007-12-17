package org.isharding.shard.strategy.access.exit;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

/**
 * 获取第一个非null结果的退出策略，主要处理在shars中查询一条记录是使用
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class FirstNotNullExitStrategy implements ExitStrategy {

    private Object result;

    public boolean addResult(Object result, Shard shard) {
        if (result != null) {
            this.result = result;
            return true;
        }
        return false;
    }

    public Object compileResults(ExitOperationsCollector exitOperationsCollector) {
        return this.result;
    }

}
