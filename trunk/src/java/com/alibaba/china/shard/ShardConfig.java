package com.alibaba.china.shard;

import java.util.List;

public interface ShardConfig {
    List<Shard> getAllShards();

    Shard getDefaultShard();
}
