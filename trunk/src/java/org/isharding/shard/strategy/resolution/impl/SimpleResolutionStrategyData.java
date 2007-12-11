package org.isharding.shard.strategy.resolution.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.isharding.shard.strategy.resolution.ShardResolutionStrategyData;

public class SimpleResolutionStrategyData implements ShardResolutionStrategyData {

    @SuppressWarnings("unchecked")
    private List data = new ArrayList();

    @SuppressWarnings("unchecked")
    public SimpleResolutionStrategyData(Object o) {
        this.data.add(o);
    }

    @SuppressWarnings("unchecked")
    public SimpleResolutionStrategyData(Object[] arr) {
        for (Object o : arr) {
            this.data.add(o);
        }
    }

    @SuppressWarnings("unchecked")
    public SimpleResolutionStrategyData(Collection coll) {
        this.data.addAll(coll);
    }

    @SuppressWarnings("unchecked")
    public List getData() {
        return this.data;
    }

}
