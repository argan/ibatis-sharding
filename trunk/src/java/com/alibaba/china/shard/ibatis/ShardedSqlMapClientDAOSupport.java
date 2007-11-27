package com.alibaba.china.shard.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.support.DaoSupport;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.ShardId;
import com.alibaba.china.shard.strategy.ShardStrategy;
import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategyData;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 为ibatsi提供sharding支持，目前不支持transaction和结果带排序的合并
 * 
 * 不支持求平均数，group by等需要所有节点支持的操作
 * 
 * 推荐使用时能将每次查询只分布在唯一的节点
 * 
 * @author argan
 * 
 */
public class ShardedSqlMapClientDAOSupport extends DaoSupport {

    private ShardStrategy shardStrategy;
    private SqlMapClient  sqlMapClient;
    private List<Shard>   shards;

    /**
     * Set the iBATIS Database Layer SqlMapClient to work with. Either this or a
     * "sqlMapClientTemplate" is required.
     * 
     * @see #setSqlMapClientTemplate
     */
    public final void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * Return the iBATIS Database Layer SqlMapClient that this template works
     * with.
     */
    public final SqlMapClient getSqlMapClient() {
        return this.sqlMapClient;
    }

    /**
     * Return the SqlMapClientTemplate for this DAO, pre-initialized with the
     * SqlMapClient or set explicitly.
     */
    public final Operations getSqlMapClientTemplate(ShardResolutionStrategyData data) {
        List<ShardId> shardIds = this.shardStrategy.getShardResolutionStrategy()
                .selectShardIdsFromShardResolutionStrategyData(data);
        List<Shard> shards = selectShardsByShardIds(shardIds);
        if (shards.size() <= 0) {
            throw new RuntimeException("No shard can be selected to execute.");
        }
        return new ShardedSqlMapClientTemplate(this.shardStrategy.getShardAccessStrategy(), shards, this.sqlMapClient);
    }

    /**
     * default match all shards.
     * 
     * @return
     */
    public final Operations getSqlMapClientTemplate() {
        return new ShardedSqlMapClientTemplate(this.shardStrategy.getShardAccessStrategy(), this.shards,
                this.sqlMapClient);
    }

    private List<Shard> selectShardsByShardIds(List<ShardId> shardIds) {
        List<Shard> list = new ArrayList<Shard>(this.shards.size());
        for (Shard shard : this.shards) {
            for (ShardId shardId : shardIds) {
                if (shard.getShardIds().contains(shardId) && list.contains(shard) == false) {
                    list.add(shard);
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("selected shards : %s ", list.toString()));
        }
        return list;
    }

    protected final void checkDaoConfig() {
        if (this.shards == null) {
            throw new RuntimeException("No shards specified.");
        }
        for (Shard shard : this.shards) {
            if (shard.getDataSource() == null) {
                throw new RuntimeException("No dataSource configured for shard.");
            }
        }
    }

    public List<Shard> getShards() {
        return new ArrayList<Shard>(this.shards);
    }

    public void setShards(List<Shard> shards) {
        this.shards = shards;
    }

    public void setShardStrategy(ShardStrategy shardStrategy) {
        this.shardStrategy = shardStrategy;
    }

}
