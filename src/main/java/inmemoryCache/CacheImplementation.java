package inmemoryCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link Cache}
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values maintained by this cache
 */
public class CacheImplementation<K, V> implements Cache<K, V> {
    /**
     * Map used for store cached values
     */
    final Map<K, V> map;
    /**
     * The number of elements that the cache can hold
     */
    final int capacity;
    /**
     * see {@link EvictionStrategy}
     */
    final EvictionStrategy<K> evictionStrategy;

    /**
     * Constructs empty cache with specific {@code capacuty} and {@code evictionStrategy}
     *
     * @param capacity The number of elements that the cache can hold
     * @param evictionStrategy {@link CacheImplementation#evictionStrategy}
     * @throws IllegalArgumentException if {@code capacity} less than 1
     * @throws NullPointerException if {@code evictionStrategy} is {@code null}
     */
    public CacheImplementation(int capacity, EvictionStrategy<K> evictionStrategy) {
        if (capacity < 1) throw new IllegalArgumentException("capacity must be more then 0");
        Objects.requireNonNull(evictionStrategy);
        map = new HashMap<>();
        this.capacity = capacity;
        this.evictionStrategy = evictionStrategy;
    }

    /**
     * Implementation of {@link Cache#get(Object key)}
     *
     * @param key key with which the object should be associated
     * @return value associated with {@code key} or {@code null} if no association
     */
    @Override
    public V get(K key) {
        if (map.containsKey(key)) {
            evictionStrategy.update(key);
            return map.get(key);
        }
        return null;
    }

    /**
     * Implementation of {@link Cache#put(Object key, Object value)}
     *
     * @param key   key with which the value should be associated
     * @param value value with which key is should be associated
     */
    @Override
    public void put(K key, V value) {
        if (map.size() >= capacity) {
            map.remove(evictionStrategy.evict());
        }
        map.put(key, value);
    }

    /**
     * Implementation of {@link Cache#getCapacity()}
     *
     * @return the number of elements {@link CacheImplementation#capacity} that the cache can hold
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Implementation of {@link Cache#getSize()}
     *
     * @return the number of cached values in cache
     */
    @Override
    public int getSize() {
        return map.size();
    }

    /**
     * Implementation of {@link Cache#free()}
     * Deleting all cached values and clearing all eviction strategy mappings {@link EvictionStrategy#free()}
     */
    @Override
    public void free() {
        map.clear();
        evictionStrategy.free();
    }
}
