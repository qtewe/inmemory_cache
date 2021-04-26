package inmemoryCache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheImplementationTest {
    @Test
    public void cacheWithLruEvictionStrategy() {
        Cache<String, Long> cache = new CacheImplementation<>(3, new LruEvictionStrategy<>());

        final String[] EXPECTED_KEYS = new String[]{"First", "Second", "Third"};
        final long[] EXPECTED_VALUES = new long[]{1, 2, 3};

        for (int i = 0; i < 3; i++) {
            cache.put(EXPECTED_KEYS[i], EXPECTED_VALUES[i]);
        }

        final long[] result = new long[3];

        for (int i = 0; i < 3; i++) {
            result[i] = cache.get(EXPECTED_KEYS[i]);
        }

        assertArrayEquals(EXPECTED_VALUES, result);
    }

    @Test
    public void cacheWithLruEvictionStrategyCachingOverCapacity() {
        final int EXPECTED_CAPACITY = 10;
        Cache<Byte, String> cache = new CacheImplementation<>(EXPECTED_CAPACITY, new LruEvictionStrategy<>());

        for (int i = 0; i < 50; i++) {
            cache.put((byte) i, "value" + i);
        }

        assertEquals(EXPECTED_CAPACITY,cache.getCapacity());
    }

    @Test
    public void cacheWithLruEvictionStrategyGetNonExistingElement() {
        Cache<String, Double> cache = new CacheImplementation<>(100, new LruEvictionStrategy<>());
        assertNull(cache.get("element1"));
    }

    @Test
    public void cacheWithLfuEvictionStrategy() {
        Cache<String, Long> cache = new CacheImplementation<>(3, new LfuEvictionStrategy<>());

        final String[] EXPECTED_KEYS = new String[]{"First", "Second", "Third"};
        final long[] EXPECTED_VALUES = new long[]{1, 2, 3};

        for (int i = 0; i < 3; i++) {
            cache.put(EXPECTED_KEYS[i], EXPECTED_VALUES[i]);
        }

        final long[] result = new long[3];

        for (int i = 0; i < 3; i++) {
            result[i] = cache.get(EXPECTED_KEYS[i]);
        }

        assertArrayEquals(EXPECTED_VALUES, result);
    }

    @Test
    public void cacheWithLfuEvictionStrategyCachingOverCapacity() {
        final int EXPECTED_CAPACITY = 10;
        Cache<Byte, String> cache = new CacheImplementation<>(EXPECTED_CAPACITY, new LfuEvictionStrategy<>());

        for (int i = 0; i < 50; i++) {
            cache.put((byte) i, "value" + i);
        }

        assertEquals(EXPECTED_CAPACITY,cache.getCapacity());
    }

    @Test
    public void cacheWithLfuEvictionStrategyGetNonExistingElement() {
        Cache<String, Double> cache = new CacheImplementation<>(100, new LfuEvictionStrategy<>());
        assertNull(cache.get("element1"));
    }

    @Test
    public void cacheWithLfuEvictionStrategyCheckExistenceOfMostFrequentlyUsedObject(){
        Cache<String, String> cache = new CacheImplementation<>(5, new LfuEvictionStrategy<>());

        for (int i = 0; i < 10; i++) {
            cache.put("expectedObjectKey","expectedObjectValue");
        }

        for (int i = 0; i < 100; i++) {
            cache.put(Integer.toString(i),"value" + i);
        }

        assertEquals("expectedObjectValue",cache.get("expectedObjectKey"));
    }
}