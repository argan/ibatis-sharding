package org.isharding.shard;

import java.util.Set;

import javax.sql.DataSource;


/**
 * 
 * 代表一个Shard
 * 
 * @author <a href="mailto:argan.wang@gmail.com">Argan Wang</a>
 */
public interface Shard {
    DataSource getDataSource();
    Set<ShardId> getShardIds();
}
