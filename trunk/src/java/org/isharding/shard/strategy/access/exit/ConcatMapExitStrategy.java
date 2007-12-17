package org.isharding.shard.strategy.access.exit;

import java.util.HashMap;
import java.util.Map;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

/**
 * 对Map结果集进行合并的退出策略
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class ConcatMapExitStrategy implements ExitStrategy {
    @SuppressWarnings("unchecked")
    private Map<Shard, Map> resultMap = new HashMap<Shard, Map>();

    @SuppressWarnings("unchecked")
    public boolean addResult(Object obj, Shard shard) {
        if (obj instanceof Map) {
            this.resultMap.put(shard, (Map) obj);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public Map compileResults(ExitOperationsCollector exitOperationsCollector) {
        if (exitOperationsCollector != null) {
            return (Map) exitOperationsCollector.apply(this.resultMap);
        } else {
            Map result = new HashMap();
            for (Map tmp : this.resultMap.values()) {
                result.putAll(tmp);
            }
            return result;
        }
    }

}
