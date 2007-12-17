package org.isharding.shard;

/**
 * @author <a href="mailto:kerrigan@alibaba-inc.com">Argan Wang</a>
 */
public class ShardId {

    private final int shardId;

    public ShardId(int shardId){
        this.shardId = shardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ShardId shardId1 = (ShardId) o;

        return shardId == shardId1.shardId;
    }

    @Override
    public int hashCode() {
        return shardId;
    }

    public int getId() {
        return shardId;
    }

    @Override
    public String toString() {
        return Integer.toString(shardId);
    }
}
