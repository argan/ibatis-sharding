package org.isharding.shard.ibatis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import org.isharding.shard.Shard;

public class SqlTranslatorHelper {
    private static Map<Shard, SQLExceptionTranslator> translatorMap = new ConcurrentHashMap<Shard, SQLExceptionTranslator>();

    public static SQLExceptionTranslator getExceptionTranslator(Shard shard) {
        SQLExceptionTranslator translator = translatorMap.get(shard);
        if (translator == null) {
            DataSource dataSource = shard.getDataSource();
            if (dataSource != null) {
                translator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            } else {
                translator = new SQLStateSQLExceptionTranslator();
            }
            translatorMap.put(shard, translator);
        }
        return translator;
    }
}
