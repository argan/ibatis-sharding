package com.alibaba.china.shard.strategy.access.exit;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;

public class ConcatExitStrategy implements ExitStrategy {

    @SuppressWarnings("unchecked")
    private List result = new ArrayList();

    @SuppressWarnings("unchecked")
    public boolean addResult(Object obj, Shard shard) {
        if (obj instanceof List) {
            this.result.addAll((List) obj);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public List compileResults(ExitOperationsCollector exitOperationsCollector) {
        return this.result;
    }

}
