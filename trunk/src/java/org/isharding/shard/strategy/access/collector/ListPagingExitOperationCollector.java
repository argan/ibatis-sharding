package org.isharding.shard.strategy.access.collector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

public class ListPagingExitOperationCollector implements ExitOperationsCollector {
    @SuppressWarnings( { "unchecked" })
    private Comparator comparator;

    public ListPagingExitOperationCollector() {

    }

    @SuppressWarnings("unchecked")
    public ListPagingExitOperationCollector(Comparator comparator) {
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
