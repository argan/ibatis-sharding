package com.alibaba.china.shard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.china.shard.ibatis.ShardedSqlMapClientDAOSupport;

public class SimpleDAO extends ShardedSqlMapClientDAOSupport {
    public void insertEntity(TestEntity entity) {
        this.getSqlMapClientTemplate(new LoginNameResolutionStrategyData(entity.getLoginName())).insert(
                "ms-insert-test", entity);
    }

    public TestEntity getEntity(String loginName) {
        return (TestEntity) this.getSqlMapClientTemplate(new LoginNameResolutionStrategyData(loginName))
                .queryForObject("ms-select-test-by-loginName", loginName);
    }

    public List<TestEntity> getEntities(String[] loginNames) {
        Map<String, String[]> param = new HashMap<String, String[]>(1);
        param.put("loginNames", loginNames);
        return this.getSqlMapClientTemplate(new LoginNameResolutionStrategyData(loginNames)).queryForList(
                "ms-select-test-by-loginName-array", param);
    }

    public int getTotalCount() {
        return this.getSqlMapClientTemplate().queryForCount("ms-select-total-count", null);
    }
}
