package inmemoryCache;

import java.util.HashMap;

/**
 * Implementation of Least Recently Used eviction strategy with O(1) update and evict actions
 *
 * @param <K> the type of keys maintained by this strategy
 */
public class LruEvictionStrategy<K> implements EvictionStrategy<K> {
    private static class Node<K> {
        Node<K> previous;
        Node<K> next;
        K key;

        Node(K key, Node<K> previous, Node<K> next) {
            this.key = key;
            this.previous = previous;
            this.next = next;
        }
    }

    /**
     * Map used to fast access to the {@code node} associated with {@code key}
     */
    private HashMap<K, Node<K>> map = new HashMap<>();

    /**
     * Object that hold lest recently used {@code key}
     */
    private Node<K> leastRecentlyUsed = null;
    /**
     * Object that hold most recently used {@code key}
     */
    private Node<K> mostRecentlyUsed = null;

    /**
     * Implementation of {@link EvictionStrategy#evict()}
     *
     * @return key {@code k} for pair that strategy decided to evict or {@code null} if strategy mappings is empty
     */
    @Override
    public K evict() {
        if (leastRecentlyUsed == null) return null;
        Node<K> temp = leastRecentlyUsed;
        if (leastRecentlyUsed.next != null) {
            leastRecentlyUsed.next.previous = null;
            leastRecentlyUsed = leastRecentlyUsed.next;
        }
        map.remove(temp.key);

        return temp.key;
    }

    /**
     * Implementation of {@link EvictionStrategy#update(Object key)}
     *
     * @param key adds strategy association with key if mapping not exist or updating condition of mapping
     */
    @Override
    public void update(K key) {
        final Node<K> node;

        if (map.containsKey(key)) {
            node = map.get(key);
        } else {
            node = new Node<>(key, mostRecentlyUsed, null);
            map.put(key, node);
        }

        if (mostRecentlyUsed == null && leastRecentlyUsed == null) {
            mostRecentlyUsed = node;
            leastRecentlyUsed = node;
            mostRecentlyUsed.previous = leastRecentlyUsed;
            leastRecentlyUsed.next = mostRecentlyUsed;
        }

        if (node != mostRecentlyUsed) {
            if (node == leastRecentlyUsed) {
                leastRecentlyUsed = node.next;
                if (node.next != null)
                    node.next.previous = null;
            } else {
                Node<K> next = node.next;
                Node<K> previous = node.previous;
                if (previous != null) previous.next = next;
                if (next != null) next.previous = previous;
            }
            mostRecentlyUsed.next = node;
            mostRecentlyUsed = node;
        }
    }

    /**
     * Implementation of {@link EvictionStrategy#free()}
     *
     * Deletes all mappings
     */
    @Override
    public void free() {
        map.clear();
        leastRecentlyUsed = null;
        mostRecentlyUsed = null;
    }
}
