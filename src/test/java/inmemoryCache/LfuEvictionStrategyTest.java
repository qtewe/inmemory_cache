package inmemoryCache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LfuEvictionStrategyTest {
    @Test
    public void updateAndEvictElement() {
        LfuEvictionStrategy<Integer> strategy = new LfuEvictionStrategy<>();
        strategy.update(13);
        assertEquals(13, strategy.evict());
    }

    @Test
    public void updateAndEvictListOfElements() {
        String[] EXPECTED_KEYS = new String[]{"one", "two", "three", "four", "five"};

        LfuEvictionStrategy<String> strategy = new LfuEvictionStrategy<>();

        for (String key : EXPECTED_KEYS) {
            strategy.update(key);
        }

        String[] result = new String[5];

        for (int i = 0; i < result.length; i++) {
            result[i] = strategy.evict();
        }

        assertArrayEquals(EXPECTED_KEYS,result);
    }

    @Test
    public void evictEmptyStrategyCache(){
        LfuEvictionStrategy<Byte> strategy = new LfuEvictionStrategy<>();
        assertNull(strategy.evict());
    }
}