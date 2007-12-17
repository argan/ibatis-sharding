package org.isharding.shard.strategy.access.collector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.isharding.shard.Shard;
import org.isharding.shard.strategy.access.ExitOperationsCollector;

/**
 * 对queryForList的结果进行排序和分页的处理器
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public class ListPagingExitOperationCollector implements ExitOperationsCollector {

    private static Log logger = LogFactory.getLog(ListPagingExitOperationCollector.class);

    @SuppressWarnings( { "unchecked" })
    private Comparator comparator;
    private int        start;
    private int        end;

    /**
     * 
     * @param start
     *            zero based index.
     * @param end
     */
    public ListPagingExitOperationCollector(int start, int end) {
        this(start, end, null);
    }

    @SuppressWarnings("unchecked")
    public ListPagingExitOperationCollector(int start, int end, Comparator comparator) {
        this.start = start;
        this.end = end;
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
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("sorting %d elements.", result.size()));
        }
        if (this.comparator != null) {
            Collections.sort(result, this.comparator);
        } else {
            Collections.sort(result, new Comparator() {

                public int compare(Object o1, Object o2) {
                    return o2.hashCode() - o1.hashCode();
                }

            });
        }
        return sublist(result, this.start, this.end);
    }

    @SuppressWarnings("unchecked")
    private List sublist(List result, int start2, int end2) {
        int size = result.size();
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("fetch sublist %d to %d from %d elements.", start2,end2,result.size()));
        }
        if (start2 < 0 || end2 < 0) {
            return null;
        }
        if (start2 >= size) {
            return null;
        }
        if (end2 > size) {
            end2 = size;
        }
        return result.subList(start2, end2);
    }

}
