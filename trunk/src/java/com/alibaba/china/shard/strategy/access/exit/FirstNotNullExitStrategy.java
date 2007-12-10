package com.alibaba.china.shard.strategy.access.exit;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;

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
