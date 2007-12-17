package org.isharding.shard.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;

import org.isharding.shard.Shard;
import org.isharding.shard.ShardConfig;
import org.isharding.shard.ShardId;

/**
 * ≈‰÷√–≈œ¢
 * 
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class DefaultShardConfig implements ShardConfig, InitializingBean {

    private Map<DataSource, String> dataSourceMapping;
    private DataSource              defaultDataSource;
    private List<Shard>             shards;
    private Shard                   defaultShard;

    public List<Shard> getAllShards() {
        return this.shards;
    }

    public Shard getDefaultShard() {
        return this.defaultShard;
    }

    public void afterPropertiesSet() throws Exception {
        if (this.dataSourceMapping == null && this.defaultDataSource == null) {
            throw new RuntimeException("You must set property 'dataSourceMapping' or 'defaultDataSource'.");
        }
        if (this.dataSourceMapping.size() <= 0 && this.defaultDataSource == null) {
            throw new RuntimeException("There must be at least one entry in property 'dataSourceMapping'.");
        }

        createShards();
    }

    private void createShards() {
        /**
         * check and create defaultShard
         */
        if (this.defaultDataSource != null) {
            this.defaultShard = new ShardImpl(this.defaultDataSource, new HashSet<ShardId>() {
                private static final long serialVersionUID = -4095293282854720799L;

                @Override
                public boolean contains(Object o) {
                    return true;
                }

            });
        }
        /**
         * create other shards
         */
        List<Shard> allShards = new ArrayList<Shard>(this.dataSourceMapping.size());
        for (DataSource ds : this.dataSourceMapping.keySet()) {
            allShards.add(new ShardImpl(ds, this.dataSourceMapping.get(ds)));
        }

        /**
         * make the shards list un-modifiable
         */
        this.shards = Collections.unmodifiableList(allShards);
    }

    public void setDataSourceMapping(Map<DataSource, String> dataSourceMapping) {
        this.dataSourceMapping = dataSourceMapping;
    }

    public void setDefaultDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

}
