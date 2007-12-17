package org.isharding.shard.ibatis;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;
import org.isharding.shard.strategy.access.ShardAccessStrategy;
import org.isharding.shard.strategy.access.collector.ListSortExitOperationCollector;
import org.isharding.shard.strategy.access.exit.ConcatListExitStrategy;
import org.isharding.shard.strategy.access.exit.ConcatMapExitStrategy;
import org.isharding.shard.strategy.access.exit.ExitStrategy;
import org.isharding.shard.strategy.access.exit.FirstNotNullExitStrategy;
import org.isharding.shard.strategy.access.exit.SumExitStrategy;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class ShardedSqlMapClientTemplate implements Operations {
    protected final Log         logger = LogFactory.getLog(getClass());

    private ShardAccessStrategy shardAccessStrategy;
    private List<Shard>         shards;
    private SqlMapClient        sqlMapClient;

    public ShardedSqlMapClientTemplate(ShardAccessStrategy shardAccessStrategy, List<Shard> shards,
            SqlMapClient sqlMapClient) {
        this.shards = shards;
        this.shardAccessStrategy = shardAccessStrategy;
        this.sqlMapClient = sqlMapClient;
    }

    public final Object execute(SqlMapClientCallback callback, ExitStrategy exitStrategy) {
        return this.execute(callback, "execute()", exitStrategy, null);
    }

    public final Object execute(SqlMapClientCallback callback, String opName, ExitStrategy exitStrategy) {
        return this.execute(callback, opName, exitStrategy, null);
    }

    public final Object execute(SqlMapClientCallback callback, String opName, ExitStrategy exitStrategy,
            ExitOperationsCollector collector) {
        return this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback, opName,
                this.sqlMapClient), exitStrategy, collector);
    }

    public int delete(final String statementName, final Object parameterObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.delete(statementName, parameterObject);
            }

        };

        Integer result = (Integer) this.execute(callback, new SumExitStrategy());
        return result == null ? 0 : result.intValue();
    }

    public Object insert(final String statementName, final Object parameterObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.insert(statementName, parameterObject);
            }

        };

        return this.execute(callback, "insert(" + statementName + ")", new FirstNotNullExitStrategy());
    }

    @SuppressWarnings("unchecked")
    public List queryForList(final String statementName, final Object parameterObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName, parameterObject);
            }

        };

        return (List) this.execute(callback, "queryForList(" + statementName + ")", new ConcatListExitStrategy());
    }

    @SuppressWarnings("unchecked")
    public List queryForListSorted(final String statementName, final Object parameterObject, Comparator comparator) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName, parameterObject);
            }

        };

        return (List) this.execute(callback, "queryForListSorted(" + statementName + ")", new ConcatListExitStrategy(),
                new ListSortExitOperationCollector(comparator));
    }

    @SuppressWarnings("unchecked")
    public List queryForList(final String statementName) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName);
            }

        };

        return (List) this.execute(callback, "queryForList(" + statementName + ")", new ConcatListExitStrategy());
    }

    @SuppressWarnings("unchecked")
    public List queryForListSorted(final String statementName, Comparator comparator) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName);
            }

        };

        return (List) this.execute(callback, "queryForListSorted(" + statementName + ")", new ConcatListExitStrategy(),
                new ListSortExitOperationCollector(comparator));
    }

    @SuppressWarnings("unchecked")
    public Map queryForMap(final String statementName, final Object parameterObject, final String keyProperty) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForMap(statementName, parameterObject, keyProperty);
            }

        };

        return (Map) this.execute(callback, "queryForMap(" + statementName + ")", new ConcatMapExitStrategy());
    }

    @SuppressWarnings("unchecked")
    public Map queryForMap(final String statementName, final Object parameterObject, final String keyProperty,
            final String valueProperty) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForMap(statementName, parameterObject, keyProperty, valueProperty);
            }

        };

        return (Map) this.execute(callback, "queryForMap(" + statementName + ")", new ConcatMapExitStrategy());
    }

    public Object queryForObject(final String statementName, final Object parameterObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForObject(statementName, parameterObject);
            }

        };

        return this.execute(callback, "queryForObject(" + statementName + ")", new FirstNotNullExitStrategy());
    }

    public Object queryForObject(final String statementName, final Object parameterObject, final Object resultObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForObject(statementName, parameterObject, resultObject);
            }

        };

        return this.execute(callback, "queryForObject(" + statementName + ")", new FirstNotNullExitStrategy());
    }

    public int update(final String statementName, final Object parameterObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.update(statementName, parameterObject);
            }

        };

        Integer result = (Integer) this.execute(callback, "update(" + statementName + ")", new SumExitStrategy());
        return result;
    }

    public int queryForCount(final String statementName, final Object parameterObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForObject(statementName, parameterObject);
            }

        };

        return (Integer) this.execute(callback, "queryForObject(" + statementName + ")", new SumExitStrategy());
    }

    @SuppressWarnings("unchecked")
    public List queryForList(final String statementName, final Object parameterObject,
            ExitOperationsCollector exitOperationsCollector) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName, parameterObject);
            }

        };

        return (List) this.execute(callback, "queryForList(" + statementName + ")", new ConcatListExitStrategy(),
                exitOperationsCollector);
    }

    @SuppressWarnings("unchecked")
    public List queryForList(final String statementName, ExitOperationsCollector exitOperationsCollector) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName);
            }

        };

        return (List) this.execute(callback, "queryForList(" + statementName + ")", new ConcatListExitStrategy(),
                exitOperationsCollector);
    }

    @SuppressWarnings("unchecked")
    public List queryForListSorted(final String statementName, final Object parameterObject) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName, parameterObject);
            }

        };

        return (List) this.execute(callback, "queryForListSorted(" + statementName + ")", new ConcatListExitStrategy(),
                new ListSortExitOperationCollector());

    }

    @SuppressWarnings("unchecked")
    public List queryForListSorted(final String statementName) {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName);
            }

        };

        return (List) this.execute(callback, "queryForListSorted(" + statementName + ")", new ConcatListExitStrategy(),
                new ListSortExitOperationCollector());
    }

}
