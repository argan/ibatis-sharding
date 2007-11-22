package com.alibaba.china.shard.strategy.access;


public interface ExitOperationsCollector {

    Object apply(Object result);
    
}
