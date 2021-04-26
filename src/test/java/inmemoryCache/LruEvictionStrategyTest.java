package inmemoryCache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LruEvictionStrategyTest {
    @Test
    public void updateAndEvictElements() {
        EvictionStrategy<Integer> strategy = new LruEvictionStrategy<>();
        for (int i = 1; i < 11; i++) {
            strategy.update(i);
        }
        for (int i = 1; i < 11; i++) {
            assertEquals(i,strategy.evict());
        }
    }

    @Test
    public void evict() {
        EvictionStrategy<String> strategy = new LruEvictionStrategy<>();
        strategy.update("key1");
        strategy.update("key2");
        strategy.update("key1");
        assertEquals("key2",strategy.evict());
    }

    @Test
    public void evictEmpty() {
        EvictionStrategy<Long> strategy = new LruEvictionStrategy<>();
        assertEquals(null,strategy.evict());
    }
}