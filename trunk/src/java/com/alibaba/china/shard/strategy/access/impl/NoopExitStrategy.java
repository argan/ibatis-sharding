package com.alibaba.china.shard.strategy.access.impl;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;
import com.alibaba.china.shard.strategy.access.ExitStrategy;

public class NoopExitStrategy  implements ExitStrategy  {

    public boolean addResult(Object result, Shard shard) {
        return false;
    }

    public Object compileResults(ExitOperationsCollector exitOperationsCollector) {
        return null;
    }

}
