package org.isharding.shard.strategy.access;

import org.isharding.shard.Shard;

public interface ShardOperation {
    Object execute(Shard shard);
    
    String getOperationName();
}
