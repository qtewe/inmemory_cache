package inmemoryCache;

public interface EvictionStrategy<K> {
    K evict();

    void update(K key);

    void free();
}
