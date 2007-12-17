package org.isharding.shard;

import java.util.List;

/**
 * Config infomation about sharding
 * 
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public interface ShardConfig {
    List<Shard> getAllShards();

    Shard getDefaultShard();
}
