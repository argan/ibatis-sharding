package com.alibaba.china.shard;

import com.alibaba.china.shard.ibatis.ShardedSqlMapClientDAOSupport;

public class SimpleDAO extends ShardedSqlMapClientDAOSupport {
    public void insertEntity(TestEntity entity){
       this.getSqlMapClientTemplate(new LoginNameResolutionStrategyData(entity.getLoginName())).insert("ms-insert-test", entity);
    }
}
