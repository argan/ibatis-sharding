package com.alibaba.china.shard.strategy.access;

import java.util.Map;

import com.alibaba.china.shard.Shard;


public interface ExitOperationsCollector {

    Object apply(Map<Shard,?> result);
    
}
