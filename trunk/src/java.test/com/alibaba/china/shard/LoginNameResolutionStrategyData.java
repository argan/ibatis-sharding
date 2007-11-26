package com.alibaba.china.shard;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.china.shard.strategy.resolution.ShardResolutionStrategyData;

public class LoginNameResolutionStrategyData implements ShardResolutionStrategyData {

    private List<String> loginNames = new ArrayList<String>(1);

    public LoginNameResolutionStrategyData(String loginName) {
        this.loginNames.add(loginName);
    }

    public LoginNameResolutionStrategyData(String[] loginNames) {
        for (String loginName : loginNames) {
            this.loginNames.add(loginName);
        }
    }

    public List<String> getData() {
        return this.loginNames;
    }

}
