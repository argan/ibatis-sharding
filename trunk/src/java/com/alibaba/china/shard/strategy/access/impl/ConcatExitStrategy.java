package com.alibaba.china.shard.strategy.access.impl;

import java.util.List;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;
import com.alibaba.china.shard.strategy.access.ExitStrategy;

public class ConcatExitStrategy implements ExitStrategy {

    private List result;

    public boolean addResult(Object obj, Shard shard) {
        if (obj instanceof List) {
            this.result.addAll((List) obj);
        }
        return false;
    }

    public Object compileResults(ExitOperationsCollector exitOperationsCollector) {
        return this.result;
    }

}
