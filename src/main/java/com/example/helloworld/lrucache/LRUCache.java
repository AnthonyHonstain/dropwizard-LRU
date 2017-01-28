package com.example.helloworld.lrucache;

import java.util.HashMap;

/**
 * Created by Anthony Honstain on 1/28/17.
 */
public class LRUCache {

    private int maxCapacity;
    private int size;

    private HashMap<String, CacheValue> cache;
    private PriorityLinkedList priorityLinkedList;

    public LRUCache(int maxCapacity) {
        // TODO - initial stub values to get the ball rolling.
        this.maxCapacity = maxCapacity;
        size = 0;

        cache = new HashMap<>();
        priorityLinkedList = new PriorityLinkedList(maxCapacity);
    }

    public String get(String key) {
        CacheValue cacheValue = cache.get(key);

        if (cacheValue == null) {
            return null;
        }
        String value = cacheValue.value;
        priorityLinkedList.touch(cacheValue.priorityNode);
        return value;
    }

    public void set(String key, String value) {
        if (value == null) {
            throw new RuntimeException("null value is not supported");
        }

        if (size == maxCapacity) {
            PriorityNode oldestNode = priorityLinkedList.removeOldest();
            String oldestKey = oldestNode.getKey();
            cache.remove(oldestKey);

            PriorityNode priorityNode = new PriorityNode(key);
            CacheValue cacheValue = new CacheValue(value, priorityNode);
            cache.put(key, cacheValue);
            assert(size == maxCapacity);
            priorityLinkedList.insert(priorityNode);
        }
        else {
            PriorityNode priorityNode = new PriorityNode(key);
            CacheValue cacheValue = new CacheValue(value, priorityNode);
            cache.put(key, cacheValue);
            size += 1;
            assert(size == cache.size());
            priorityLinkedList.insert(priorityNode);
        }
    }

    public class CacheValue {
        private String value;
        private PriorityNode priorityNode;

        public CacheValue(String value, PriorityNode priorityNode) {
            this.value = value;
            this.priorityNode = priorityNode;
        }
    }
}

