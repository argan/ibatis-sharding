package org.isharding.shard.impl;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.isharding.shard.Shard;
import org.isharding.shard.ShardId;

/**
 * 默认的Shard实现，主要是包装dataSource和对应的shardId列表
 * 
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 * 
 */
public class ShardImpl implements Shard {
    private final Log    logger = LogFactory.getLog(getClass());

    private DataSource   dataSource;
    private Set<ShardId> shardIds;

    public ShardImpl(DataSource dataSource, String shardIdSet) {
        this.dataSource = dataSource;
        this.shardIds = computeShardIds(shardIdSet);
    }

    public ShardImpl(DataSource dataSource, Set<ShardId> shardIdSet) {
        this.dataSource = dataSource;
        this.shardIds = shardIdSet;
    }

    /**
     * 格式:1-5,8,9,10 == <1,2,3,4,5,8,9,10>
     * 
     * @param shardIdSet
     * @return
     */
    private Set<ShardId> computeShardIds(String shardIdSet) {
        if (StringUtils.isEmpty(shardIdSet)) {
            return new HashSet<ShardId>(0);
        }
        Set<ShardId> set = new HashSet<ShardId>();
        String[] arr = StringUtils.split(shardIdSet, ',');
        for (String s : arr) {
            set.addAll(parse(s));
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Parseing string %s ,result: %s.", shardIdSet, set.toString()));
        }
        return set;
    }

    /**
     * 有两种情况： 1.只有数字 2.数字区间
     * 
     * @param s
     * @return
     */
    private Set<ShardId> parse(String s) {
        Set<ShardId> set = new HashSet<ShardId>();
        int index = s.indexOf("-");
        if (index > 0) {
            // a range
            int start = Integer.parseInt(s.substring(0, index));
            int end = Integer.parseInt(s.substring(index + 1));

            for (int i = start; i <= end; i++) {
                set.add(new ShardId(i));
            }
        } else {
            // just a int
            set.add(new ShardId(Integer.parseInt(s)));
        }
        return set;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public Set<ShardId> getShardIds() {
        return new HashSet<ShardId>(this.shardIds);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataSource == null) ? 0 : dataSource.hashCode());
        result = prime * result + ((shardIds == null) ? 0 : shardIds.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ShardImpl other = (ShardImpl) obj;
        if (dataSource == null) {
            if (other.dataSource != null)
                return false;
        } else if (!dataSource.equals(other.dataSource))
            return false;
        if (shardIds == null) {
            if (other.shardIds != null)
                return false;
        } else if (!shardIds.equals(other.shardIds))
            return false;
        return true;
    }

    public String toString() {
        return String.format("Shard with dataSource[%s],mapping to shardIds: %s", String.valueOf(this.dataSource),
                String.valueOf(this.shardIds));
    }
}
