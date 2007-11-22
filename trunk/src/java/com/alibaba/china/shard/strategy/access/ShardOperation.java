package com.alibaba.china.shard.strategy.access;

import com.alibaba.china.shard.Shard;

public interface ShardOperation {
    Object execute(Shard shard);
    
    String getOperationName();
}
