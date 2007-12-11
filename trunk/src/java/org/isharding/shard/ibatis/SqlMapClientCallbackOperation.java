package org.isharding.shard.ibatis;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ShardOperation;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;

public class SqlMapClientCallbackOperation implements ShardOperation {
    protected final Log            logger = LogFactory.getLog(getClass());

    private SQLExceptionTranslator exceptionTranslator;

    private SqlMapClientCallback   callback;
    private String                 opName;
    private SqlMapClient           sqlMapClient;

    SqlMapClientCallbackOperation(SqlMapClientCallback callback, String opName, SqlMapClient sqlMapClient) {
        this.callback = callback;
        this.opName = opName;
        this.sqlMapClient = sqlMapClient;
    }

    public Object execute(Shard shard) {
        SqlMapSession session = this.sqlMapClient.openSession();
        Connection springCon = null;
        try {
            logger.debug("Opened SqlMapSession [" + session + "] for iBATIS operation");
            try {
                springCon = DataSourceUtils.getConnection(shard.getDataSource());
                session.setUserConnection(springCon);
                logger.debug("Obtained JDBC Connection [" + springCon + "] for iBATIS operation");
                return callback.doInSqlMapClient(session);
            } catch (SQLException ex) {
                throw getExceptionTranslator(shard).translate("SqlMapClient operation", null, ex);
            }
        } finally {
            DataSourceUtils.releaseConnection(springCon, shard.getDataSource());
            session.close();
        }
    }

    public String getOperationName() {
        return this.opName;
    }

    private synchronized SQLExceptionTranslator getExceptionTranslator(Shard shard) {
        if (this.exceptionTranslator == null) {
            DataSource dataSource = shard.getDataSource();
            if (dataSource != null) {
                this.exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            } else {
                this.exceptionTranslator = new SQLStateSQLExceptionTranslator();
            }
        }
        return this.exceptionTranslator;
    }
}
