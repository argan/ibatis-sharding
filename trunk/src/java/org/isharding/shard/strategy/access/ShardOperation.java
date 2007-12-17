package org.isharding.shard.strategy.access;

import org.isharding.shard.Shard;

/**
 * 在shards中执行的一次操作
 * 
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public interface ShardOperation {
    Object execute(Shard shard);
    
    String getOperationName();
}
