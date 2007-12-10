package com.alibaba.china.shard.strategy.access.exit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;

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
