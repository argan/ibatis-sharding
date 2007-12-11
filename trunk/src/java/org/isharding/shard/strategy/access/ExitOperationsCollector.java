package org.isharding.shard.strategy.access;

import java.util.Map;

import org.isharding.shard.Shard;


public interface ExitOperationsCollector {

    Object apply(Map<Shard,?> result);
    
}
