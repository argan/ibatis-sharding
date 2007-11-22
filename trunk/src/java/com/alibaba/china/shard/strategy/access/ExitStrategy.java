package com.alibaba.china.shard.strategy.access;

import com.alibaba.china.shard.Shard;

public interface ExitStrategy {

    boolean addResult(Object result, Shard shard);

    Object compileResults(ExitOperationsCollector exitOperationsCollector);
}
