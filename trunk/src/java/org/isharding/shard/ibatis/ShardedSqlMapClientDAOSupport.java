package org.isharding.shard.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.support.DaoSupport;

import org.isharding.shard.Shard;
import org.isharding.shard.ShardConfig;
import org.isharding.shard.ShardId;
import org.isharding.shard.strategy.ShardStrategy;
import org.isharding.shard.strategy.resolution.ShardResolutionStrategyData;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 为ibatis提供sharding支持，目前不支持transaction
 * 
 * 目前不支持求平均数，group by等需要所有节点支持的操作
 * 
 * 推荐使用时能将每次查询只分布在唯一的节点
 * 
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 * 
 */
public class ShardedSqlMapClientDAOSupport extends DaoSupport {

    private ShardStrategy shardStrategy;
    private SqlMapClient  sqlMapClient;
    private ShardConfig   shardConfig;

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
        return new ShardedSqlMapClientTemplate(this.shardStrategy.getShardAccessStrategy(), this.shardConfig
                .getAllShards(), this.sqlMapClient);
    }

    private List<Shard> selectShardsByShardIds(List<ShardId> shardIds) {
        List<Shard> list = new ArrayList<Shard>(this.shardConfig.getAllShards().size());
        for (Shard shard : this.shardConfig.getAllShards()) {
            for (ShardId shardId : shardIds) {
                if (shard.getShardIds().contains(shardId) && list.contains(shard) == false) {
                    list.add(shard);
                }
            }
        }
        if (list.size() > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("selected shards : %s ", list.toString()));
            }
        } else {
            if (this.shardConfig.getDefaultShard() != null) {
                list.add(this.shardConfig.getDefaultShard());

                if (logger.isDebugEnabled()) {
                    logger.debug("No shard selected ,use defaultShard.");
                }
            } else {
                logger.error("No shard selected and no defaultShard configed.");
            }
        }
        return list;
    }

    protected final void checkDaoConfig() {
        if (this.shardConfig == null) {
            throw new RuntimeException("No shard config specified.");
        }
        for (Shard shard : this.shardConfig.getAllShards()) {
            if (shard.getDataSource() == null) {
                throw new RuntimeException("No dataSource configured for shard.");
            }
        }
    }

    public List<Shard> getShards() {
        return this.shardConfig.getAllShards();
    }

    public void setShardConfig(ShardConfig shardConfig) {
        this.shardConfig = shardConfig;
    }

    public void setShardStrategy(ShardStrategy shardStrategy) {
        this.shardStrategy = shardStrategy;
    }

}
