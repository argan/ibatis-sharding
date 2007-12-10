package com.alibaba.china.shard.strategy.access.collector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.alibaba.china.shard.Shard;
import com.alibaba.china.shard.strategy.access.ExitOperationsCollector;

public class ListSortExitOperationCollector implements ExitOperationsCollector {
    @SuppressWarnings( { "unchecked" })
    private Comparator comparator;

    public ListSortExitOperationCollector() {

    }

    @SuppressWarnings("unchecked")
    public ListSortExitOperationCollector(Comparator comparator) {
        this.comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    public List apply(Map<Shard, ?> map) {
        List result = new ArrayList();
        for (Object obj : map.values()) {
            if (obj instanceof Collection) {
                result.addAll((Collection) obj);
            }
        }
        if (this.comparator != null) {
            Collections.sort(result, this.comparator);
        } else {
            Collections.sort(result);
        }
        return result;
    }

}
