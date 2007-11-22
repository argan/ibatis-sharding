package com.alibaba.china.shard.strategy.access;

import java.util.List;

import com.alibaba.china.shard.Shard;

public interface ShardAccessStrategy {
    Object apply(List<Shard> shards, ShardOperation operation, ExitStrategy exitStrategy, ExitOperationsCollector exitOperationsCollector);
    Object apply(List<Shard> shards, ShardOperation operation, ExitStrategy exitStrategy);
}
