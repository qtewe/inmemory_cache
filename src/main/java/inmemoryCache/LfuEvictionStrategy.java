package inmemoryCache;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Implementation of Least Frequently Used eviction strategy with O(1) update and evict actions
 *
 * @param <K> the type of keys maintained by this strategy
 */
public class LfuEvictionStrategy<K> implements EvictionStrategy<K> {
    /**
     * Map used to keep number of attempts to key
     */
    private HashMap<K, Integer> usages = new HashMap<>();
    /**
     * Map used to keep number of attempts to key by number of attempts
     */
    private HashMap<Integer, LinkedHashSet<K>> list = new HashMap<>();
    /**
     * Value that hold number of attempts for least frequently used key
     */
    private int leastNumberOfUses = -1;

    /**
     * Implementation of {@link EvictionStrategy#evict()}
     *
     * @return key {@code k} for pair that strategy decided to evict or {@code null} if strategy mappings is empty
     */
    @Override
    public K evict() {
        LinkedHashSet<K> keys = list.get(leastNumberOfUses);

        if (keys == null) return null;

        K key = keys.iterator().next();
        list.get(leastNumberOfUses).remove(key);
        usages.remove(key);

        return key;
    }

    /**
     * Implementation of {@link EvictionStrategy#update(Object key)}
     *
     * @param key adding strategy association with key if mapping not exist or updating condition of mapping
     */
    @Override
    public void update(K key) {
        if (usages.containsKey(key)) {
            int usageAmount = usages.get(key);
            list.get(usageAmount).remove(key);
            usages.put(key, usageAmount + 1);

            if (usageAmount == leastNumberOfUses && list.get(usageAmount).size() == 0) {
                leastNumberOfUses++;
            }

            if (!list.containsKey(usageAmount + 1)) {
                list.put(usageAmount + 1, new LinkedHashSet<>());
            }
            list.get(usageAmount + 1).add(key);
        } else {
            usages.put(key, 1);
            if (!list.containsKey(1)) {
                list.put(1, new LinkedHashSet<>());
            }
            list.get(1).add(key);
            leastNumberOfUses = 1;
        }
    }

    /**
     * Implementation of {@link EvictionStrategy#free()}
     *
     * Deleting all mappings
     */
    @Override
    public void free() {
        usages.clear();
        list.clear();
    }
}
