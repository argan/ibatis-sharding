package org.isharding.shard;

import java.util.Set;

import javax.sql.DataSource;


/**
 * 
 * 代表一个Shard
 * 
 * @author argan 2007-11-13 下午03:06:50
 */
public interface Shard {
    DataSource getDataSource();
    Set<ShardId> getShardIds();
}
