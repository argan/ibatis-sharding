package com.alibaba.china.shard.strategy.access.impl;

import java.util.Map;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;
import com.alibaba.china.shard.strategy.access.ExitStrategy;

public class ConcatMapExitStrategy implements ExitStrategy {
    @SuppressWarnings("unchecked")
    private Map result;
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
