package inmemoryCache;

/**
 * An object that produce interface for caching objects
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values maintained by this cache
 */
public interface Cache<K,V> {
    /**
     * Returns value associated with key {@code key} or {@code null} if no association
     *
     * @param key key with which the object should be associated
     * @return value associated with {@code key} or {@code null} if no association
     */
    V get(K key);

    /**
     * Adding pair of {@code key} and {@code value} to the cache
     *
     * @param key key with which the value should be associated
     * @param value value with which key is should be associated
     */
    void put(K key, V value);

    /**
     * Return the number of elements that cache can hold
     *
     * @return the number of elements that the cache can hold
     */
    int getCapacity();

    /**
     * Returns the number of cached values in cache
     *
     * @return the number of cached values in cache
     */
    int getSize();

    /**
     * Deleting all cached values from cache
     */
    void free();
}
