package inmemoryCache;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Implementation of Least Frequently Used eviction strategy
 *
 * @param <K>
 */
public class LfuEvictionStrategy<K> implements EvictionStrategy<K> {
    private HashMap<K, Integer> usages = new HashMap<>();
    private HashMap<Integer, LinkedHashSet<K>> list = new HashMap<>();
    private int leastNumberOfUses = -1;

    @Override
    public K evict() {
        LinkedHashSet<K> keys = list.get(leastNumberOfUses);

        if (keys == null) return null;

        K key = keys.iterator().next();
        list.get(leastNumberOfUses).remove(key);
        usages.remove(key);

        return key;
    }

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

    @Override
    public void free() {
        usages.clear();
        list.clear();
    }
}
