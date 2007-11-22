package com.alibaba.china.shard.ibatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.alibaba.china.shard.Operations;
import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitStrategy;
import com.alibaba.china.shard.strategy.access.ShardAccessStrategy;
import com.alibaba.china.shard.strategy.access.impl.ConcatExitStrategy;
import com.alibaba.china.shard.strategy.access.impl.ConcatMapExitStrategy;
import com.alibaba.china.shard.strategy.access.impl.FirstNotNullExitStrategy;
import com.alibaba.china.shard.strategy.access.impl.SumExitStrategy;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

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

    /**
     * Execute the given data access action on a SqlMapSession.
     * 
     * @param action
     *            callback object that specifies the data access action
     * @return a result object returned by the action, or <code>null</code>
     * @throws DataAccessException
     *             in case of SQL Maps errors
     */
    public final Object execute(SqlMapClientCallback callback, ExitStrategy exitStrategy) throws DataAccessException {
        return this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback, "execute()",
                this.sqlMapClient), exitStrategy);
    }

    public int delete(final String statementName, final Object parameterObject) throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.delete(statementName, parameterObject);
            }

        };

        Integer result = (Integer) this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(
                callback, "delete()", this.sqlMapClient), new SumExitStrategy());
        return result;
    }

    public Object insert(final String statementName, final Object parameterObject) throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.insert(statementName, parameterObject);
            }

        };

        return this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback, "insert()",
                this.sqlMapClient), new FirstNotNullExitStrategy());
    }

    public List queryForList(final String statementName, final Object parameterObject) throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName, parameterObject);
            }

        };

        return (List) this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback,
                "queryForList()", this.sqlMapClient), new ConcatExitStrategy());
    }

    public Map queryForMap(final String statementName, final Object parameterObject, final String keyProperty)
            throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForMap(statementName, parameterObject, keyProperty);
            }

        };

        return (Map) this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback,
                "queryForMap()", this.sqlMapClient), new ConcatMapExitStrategy());
    }

    public Map queryForMap(final String statementName, final Object parameterObject, final String keyProperty,
            final String valueProperty) throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForMap(statementName, parameterObject, keyProperty, valueProperty);
            }

        };

        return (Map) this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback,
                "queryForMap()", this.sqlMapClient), new ConcatMapExitStrategy());
    }

    public Object queryForObject(final String statementName, final Object parameterObject) throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForObject(statementName, parameterObject);
            }

        };

        return this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback,
                "queryForObject()", this.sqlMapClient), new FirstNotNullExitStrategy());
    }

    public Object queryForObject(final String statementName, final Object parameterObject, final Object resultObject)
            throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForObject(statementName, parameterObject, resultObject);
            }

        };

        return this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(callback,
                "queryForObject()", this.sqlMapClient), new FirstNotNullExitStrategy());
    }

    public int update(final String statementName, final Object parameterObject) throws DataAccessException {
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.update(statementName, parameterObject);
            }

        };

        Integer result = (Integer) this.shardAccessStrategy.apply(this.shards, new SqlMapClientCallbackOperation(
                callback, "update()", this.sqlMapClient), new SumExitStrategy());
        return result;
    }
}
