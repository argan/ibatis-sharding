package com.alibaba.china.shard.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.support.DaoSupport;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.ShardId;
import com.alibaba.china.shard.Operations;
import com.alibaba.china.shard.strategy.ShardStrategy;
import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategyData;
import com.ibatis.sqlmap.client.SqlMapClient;

public class ShardedSqlMapClientDAOSupport extends DaoSupport {

	private ShardStrategy shardStrategy;
	private SqlMapClient sqlMapClient;
	private List<Shard> shards;

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
		if (shards.size()<=0){
		    throw new RuntimeException ("No shard can be selected to execute.");
		}
		return new ShardedSqlMapClientTemplate(this.shardStrategy.getShardAccessStrategy(), shards, this.sqlMapClient);
	}

	private List<Shard> selectShardsByShardIds(List<ShardId> shardIds) {
		List<Shard> list = new ArrayList<Shard>(this.shards.size());
		for (Shard shard : this.shards) {
			if (list.contains(shard) == false) {
				for (ShardId shardId : shardIds) {
					if (shard.getShardIds().contains(shardId)) {
						list.add(shard);
					}
				}
			}
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

	public void setShardStrategy(ShardStrategy shardStrategy) {
		this.shardStrategy = shardStrategy;
	}

}
