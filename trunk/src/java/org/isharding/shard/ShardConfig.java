package org.isharding.shard;

import java.util.List;

public interface ShardConfig {
    List<Shard> getAllShards();

    Shard getDefaultShard();
}
