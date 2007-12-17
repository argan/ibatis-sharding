package org.isharding.shard.strategy.selection;

import org.isharding.shard.ShardId;

/**
 * 选择策略(目前不在使用)
 * 
 * @author <a mailto="kerrigan@alibaba-inc.com">Argan Wang</a>
 */
public interface ShardSelectionStrategy {
    /**
     * 给新对象选择存放的shard 
     * @param obj 要选择shard的对象
     * @return 对象要存放到的shard的id 
     */
    ShardId selectShardIdForNewObject(Object obj);
}
