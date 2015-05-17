package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;

import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {

    private final SortedMap<Integer, CacheServiceInterface> circle = new TreeMap<Integer, CacheServiceInterface>();

    public void add(DistributedCacheService node) {
        int key = getHash(node.cacheServerUrl);
        circle.put(key, node);
    }

    public CacheServiceInterface getBucket(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = getHash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, CacheServiceInterface> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public int getHash(String key) {
        int number;
        number = Hashing.murmur3_128().newHasher().putString(key).hash().asInt();
        return number;
    }

}