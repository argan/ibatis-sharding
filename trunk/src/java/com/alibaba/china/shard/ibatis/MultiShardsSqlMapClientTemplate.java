package com.alibaba.china.shard.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;


public class MultiShardsSqlMapClientTemplate extends SqlMapClientTemplate {

    public MultiShardsSqlMapClientTemplate(List<SqlMapClientTemplate> templates){
        super();
    }
}
