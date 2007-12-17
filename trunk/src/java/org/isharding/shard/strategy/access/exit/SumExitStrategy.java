package org.isharding.shard.strategy.access.exit;

import java.util.HashMap;
import java.util.Map;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

/**
 * 将数字结果进行求和处理的退出策略
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class SumExitStrategy implements ExitStrategy {

    private Map<Shard, Integer> resultMap = new HashMap<Shard, Integer>();

    public boolean addResult(Object result, Shard shard) {
        if (result instanceof Integer)
            resultMap.put(shard, (Integer) result);
        return false;
    }

    public Integer compileResults(ExitOperationsCollector exitOperationsCollector) {
        if (exitOperationsCollector != null) {
            return (Integer) exitOperationsCollector.apply(this.resultMap);
        }
        int sum = 0;
        for (Integer count : resultMap.values()) {
            if (count != null) {
                sum += count;
            }
        }
        return sum;
    }

}
