package org.isharding.shard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.isharding.shard.ibatis.ShardedSqlMapClientDAOSupport;
import org.isharding.shard.strategy.resolution.impl.SimpleResolutionStrategyData;

public class SimpleDAO extends ShardedSqlMapClientDAOSupport {
    public void insertEntity(TestEntity entity) {
        this.getSqlMapClientTemplate(new SimpleResolutionStrategyData(entity.getLoginName())).insert(
                "ms-insert-test", entity);
    }

    public TestEntity getEntity(String loginName) {
        return (TestEntity) this.getSqlMapClientTemplate(new SimpleResolutionStrategyData(loginName))
                .queryForObject("ms-select-test-by-loginName", loginName);
    }

    @SuppressWarnings("unchecked")
    public List<TestEntity> getEntities(String[] loginNames) {
        Map<String, String[]> param = new HashMap<String, String[]>(1);
        param.put("loginNames", loginNames);
        return this.getSqlMapClientTemplate(new SimpleResolutionStrategyData(loginNames)).queryForList(
                "ms-select-test-by-loginName-array", param);
    }
    @SuppressWarnings("unchecked")
    public List<TestEntity> getEntitiesSorted(String[] loginNames) {
        Map<String, String[]> param = new HashMap<String, String[]>(1);
        param.put("loginNames", loginNames);
        return this.getSqlMapClientTemplate(new SimpleResolutionStrategyData(loginNames)).queryForListSorted(
                "ms-select-test-by-loginName-array", param , new Comparator(){

                    public int compare(Object o1, Object o2) {
                        TestEntity a = (TestEntity) o1;
                        TestEntity b = (TestEntity) o2;
                        
                        return a.getLoginName().compareTo(b.getLoginName());
                    }
                    
                });
    }
    public int getTotalCount() {
        return this.getSqlMapClientTemplate().queryForCount("ms-select-total-count", null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TestEntity> getEntitiesMap(String[] loginNames) {
        Map<String, String[]> param = new HashMap<String, String[]>(1);
        param.put("loginNames", loginNames);
        return this.getSqlMapClientTemplate(new SimpleResolutionStrategyData(loginNames)).queryForMap(
                "ms-select-test-by-loginName-array", param, "loginName");
    }
}
