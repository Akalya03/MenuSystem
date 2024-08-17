import java.util.*;
import java.time.LocalDateTime;

public class Cache<K, V> {
    private final int maxSize;
    private final long expiryDurationInSeconds;
    final LinkedHashMap<K, CacheEntry<V>> cacheMap; // Make final for controlled access

    public Cache(int maxSize, long expiryDurationInSeconds) {
        this.maxSize = maxSize;
        this.expiryDurationInSeconds = expiryDurationInSeconds;
        this.cacheMap = new LinkedHashMap<K, CacheEntry<V>>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                return size() > Cache.this.maxSize;
            }
        };
    }

    public synchronized void put(K key, V value) {
        cacheMap.put(key, new CacheEntry<>(value));
        cleanUp();
    }

    public synchronized V get(K key) {
        cleanUp();
        CacheEntry<V> entry = cacheMap.get(key);
        if (entry == null) {
            return null;
        }
        entry.updateAccessTime();
        return entry.getValue();
    }

    public synchronized void cleanUp() {
        Iterator<Map.Entry<K, CacheEntry<V>>> iterator = cacheMap.entrySet().iterator();
        System.out.println("CLEANING UP");
        while (iterator.hasNext()) {
            Map.Entry<K, CacheEntry<V>> entry = iterator.next();
            if (entry.getValue().isExpired(expiryDurationInSeconds)) {
                iterator.remove();
            }
        }
    }

    public synchronized List<V> getAllValues() {
        cleanUp();
        List<V> values = new ArrayList<>();
        for (CacheEntry<V> entry : cacheMap.values()) {
            values.add(entry.getValue());
        }
        return values;
    }

    private static class CacheEntry<V> {
        private V value;
        private LocalDateTime lastAccessTime;

        public CacheEntry(V value) {
            this.value = value;
            this.lastAccessTime = LocalDateTime.now();
        }

        public V getValue() {
            return value;
        }

        public void updateAccessTime() {
            this.lastAccessTime = LocalDateTime.now();
        }

        public boolean isExpired(long expiryDurationInSeconds) {
            return lastAccessTime.plusSeconds(expiryDurationInSeconds).isBefore(LocalDateTime.now());
        }
    }
}
