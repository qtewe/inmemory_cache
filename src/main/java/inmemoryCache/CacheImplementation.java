package inmemoryCache;

import java.util.HashMap;
import java.util.Map;

public class CacheImplementation<K, V> implements Cache<K, V> {
    final Map<K, V> map;
    final int capacity;
    final EvictionStrategy<K> evictionStrategy;

    public CacheImplementation(int capacity, EvictionStrategy<K> evictionStrategy) {
        map = new HashMap<>();
        this.capacity = capacity;
        this.evictionStrategy = evictionStrategy;
    }

    @Override
    public V get(K key) {
        if (map.containsKey(key)) {
            evictionStrategy.update(key);
            return map.get(key);
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (map.size() >= capacity) {
            map.remove(evictionStrategy.evict());
        }
        map.put(key, value);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return map.size();
    }

    @Override
    public void free() {
        map.clear();
        evictionStrategy.free();
    }
}
