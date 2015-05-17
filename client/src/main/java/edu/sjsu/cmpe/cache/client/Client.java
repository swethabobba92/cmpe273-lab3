package edu.sjsu.cmpe.cache.client;

public class Client {

    static ConsistentHashing consistentHash = new ConsistentHashing();

    public static void main(String[] args) throws Exception {
        System.out.println("---Persisting Key-Value Pair using Canonical Map in Consistent Hashing---");
        System.out.println("\nStarting Cache Client...");
        DistributedCacheService cacheA = new DistributedCacheService("http://localhost:3000");
        DistributedCacheService cacheB = new DistributedCacheService("http://localhost:3001");
        DistributedCacheService cacheC = new DistributedCacheService("http://localhost:3002");

        consistentHash.add(cacheA);
        consistentHash.add(cacheB);
        consistentHash.add(cacheC);

        CacheServiceInterface bucket;
        String[] values = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

        System.out.println("\n--PUT--");
        for (int k = 1; k <= 10; k++) {
            bucket = consistentHash.getBucket(Integer.toString(k));
            System.out.println(values[k - 1] + " ==> Server: " + (bucket.getServerURL()));
            bucket.put(Integer.toUnsignedLong(k), values[k - 1]);

        }

        System.out.println("\n--GET--");
        for (int k = 1; k <= 10; k++) {
            bucket = consistentHash.getBucket(Integer.toString(k));
            System.out.println("Server: " + (bucket.getServerURL()) + " ==> " + values[k - 1]);
        }
        System.out.println("\nExiting Cache Client...");
    }
}
