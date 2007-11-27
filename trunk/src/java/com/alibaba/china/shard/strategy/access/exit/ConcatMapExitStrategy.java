package com.alibaba.china.shard.strategy.access.exit;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;

public class ConcatMapExitStrategy implements ExitStrategy {
    @SuppressWarnings("unchecked")
    private Map result = new HashMap();
    @SuppressWarnings("unchecked")
    public boolean addResult(Object obj, Shard shard) {
        if (obj instanceof Map){
            this.result.putAll((Map)obj);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public Map compileResults(ExitOperationsCollector exitOperationsCollector) {
        return this.result;
    }

}
