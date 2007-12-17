package org.isharding.shard.strategy.access;

import java.util.Map;

import org.isharding.shard.Shard;

/**
 * sql操作结束后的处理，对结果集进行处理，如：排序，分页，合并等
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 *
 */
public interface ExitOperationsCollector {

    Object apply(Map<Shard,?> result);
    
}
