package inmemoryCache;

/**
 * An object that maps {@code k} by specific strategy and selects element for remove by this strategy
 *
 * @param <K> the type of keys maintained by this strategy
 */
public interface EvictionStrategy<K> {
    /**
     * Return {@code key} that should be evicted and deleting strategy mappings for him
     *
     * @return key {@code k} for pair that strategy decided to evict or {@code null} if strategy mappings is empty
     */
    K evict();

    /**
     * Adding strategy association with {@code key} if mapping not exist or updating condition of mapping
     *
     * @param key adding strategy association with key if mapping not exist or updating condition of mapping
     */
    void update(K key);

    /**
     * Deleting all mappings
     */
    void free();
}
