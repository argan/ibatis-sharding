package com.alibaba.china.shard.strategy.access.impl;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;
import com.alibaba.china.shard.strategy.access.ExitStrategy;

public class SumExitStrategy implements ExitStrategy {

    private int sum;

    public boolean addResult(Object result, Shard shard) {
        if (result instanceof Integer)
            this.sum += (Integer) result;
        return false;
    }

    public Integer compileResults(ExitOperationsCollector exitOperationsCollector) {
        return sum;
    }

}
