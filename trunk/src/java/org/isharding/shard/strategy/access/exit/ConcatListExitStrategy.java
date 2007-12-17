package org.isharding.shard.strategy.access.exit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

/**
 * 对List结果集进行合并的退出策略
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class ConcatListExitStrategy implements ExitStrategy {
    @SuppressWarnings("unchecked")
    private Map<Shard, List> resultMap = new HashMap<Shard, List>();

    public ConcatListExitStrategy() {
    }

    @SuppressWarnings("unchecked")
    public boolean addResult(Object obj, Shard shard) {
        if (obj instanceof List) {
            this.resultMap.put(shard, (List) obj);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public List compileResults(ExitOperationsCollector exitOperationsCollector) {
        if (exitOperationsCollector != null) {
            return (List) exitOperationsCollector.apply(this.resultMap);
        } else {
            List result = new ArrayList();
            for (List tmp : resultMap.values()) {
                result.addAll(tmp);
            }
            return result;
        }
    }

}
