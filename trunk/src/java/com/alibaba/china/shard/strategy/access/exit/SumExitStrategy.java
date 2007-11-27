package com.alibaba.china.shard.strategy.access.exit;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;

public class SumExitStrategy implements ExitStrategy {

    private Map<Shard, Integer> resultMap = new HashMap<Shard, Integer>();

    public boolean addResult(Object result, Shard shard) {
        if (result instanceof Integer)
            resultMap.put(shard, (Integer) result);
        return false;
    }

    public Integer compileResults(ExitOperationsCollector exitOperationsCollector) {
        int sum = 0;
        for (Integer count : resultMap.values()) {
            if (count != null) {
                sum += count;
            }
        }
        return sum;
    }

}
