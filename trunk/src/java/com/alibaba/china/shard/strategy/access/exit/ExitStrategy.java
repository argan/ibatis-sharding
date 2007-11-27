package com.alibaba.china.shard.strategy.access.exit;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;

public interface ExitStrategy {

    boolean addResult(Object result, Shard shard);

    Object compileResults(ExitOperationsCollector exitOperationsCollector);
}
