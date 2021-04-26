package inmemoryCache;

import java.util.HashMap;

/**
 * Implementation of Least Recently Used eviction strategy
 *
 * @param <K> key value
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

    private HashMap<K, Node<K>> map = new HashMap<>();
    private Node<K> leastRecentlyUsed = null;
    private Node<K> mostRecentlyUsed = null;

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

    @Override
    public void free() {
        map.clear();
        leastRecentlyUsed = null;
        mostRecentlyUsed = null;
    }
}
