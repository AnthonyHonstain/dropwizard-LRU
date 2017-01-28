package com.example.helloworld;

import com.example.helloworld.lrucache.LRUCache;
import com.example.helloworld.lrucache.PriorityLinkedList;
import com.example.helloworld.lrucache.PriorityNode;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anthony Honstain on 1/28/17.
 */
public class LRUCacheTest {

    @Test
    public void empty() {
        LRUCache lruCache = new LRUCache(2);
        assertEquals(null, lruCache.get("foo"));
    }

    @Test
    public void emptySetAndGet() {
        LRUCache lruCache = new LRUCache(2);
        lruCache.set("foo", "1");
        assertEquals("1", lruCache.get("foo"));
    }

    @Test
    public void smallEviction() {
        LRUCache lruCache = new LRUCache(2);
        lruCache.set("foo", "1");
        lruCache.set("bar", "2");
        lruCache.set("car", "3");

        assertEquals("2", lruCache.get("bar"));
        assertEquals("3", lruCache.get("car"));
        assertEquals(null, lruCache.get("foo"));

        lruCache.set("foo", "1");
        assertEquals(null, lruCache.get("bar"));
        assertEquals("3", lruCache.get("car"));
        assertEquals("1", lruCache.get("foo"));

    }

    @Test
    public void setFirstLinkedList() {
        PriorityLinkedList pll = new PriorityLinkedList(4);
        PriorityNode node1 = new PriorityNode("node1");

        pll.insert(node1);
        assertEquals(null, node1.getLeftNode());
        assertEquals(null, node1.getRightNode());
    }

    @Test
    public void setTwoLinkedList() {
        PriorityLinkedList pll = new PriorityLinkedList(4);
        PriorityNode node1 = new PriorityNode("node1");
        PriorityNode node2 = new PriorityNode("node2");

        pll.insert(node1);
        pll.insert(node2);
        assertEquals(null, node2.getLeftNode());
        assertEquals(node1, node2.getRightNode());
        assertEquals(node2, node1.getLeftNode());
        assertEquals(null, node1.getRightNode());
    }

    @Test
    public void setMultipleLinkedList() {
        PriorityLinkedList pll = new PriorityLinkedList(4);
        PriorityNode node1 = new PriorityNode("node1");
        PriorityNode node2 = new PriorityNode("node2");
        PriorityNode node3 = new PriorityNode("node3");

        pll.insert(node1);
        pll.insert(node2);
        pll.insert(node3);
        assertEquals(null, node3.getLeftNode());
        assertEquals(node2, node3.getRightNode());
        assertEquals(node3, node2.getLeftNode());
        assertEquals(node1, node2.getRightNode());
        assertEquals(node2, node1.getLeftNode());
        assertEquals(null, node1.getRightNode());
    }

    @Test
    public void simpleTouchLinkedList() {
        PriorityLinkedList pll = new PriorityLinkedList(4);
        PriorityNode node1 = new PriorityNode("node1");
        PriorityNode node2 = new PriorityNode("node2");
        PriorityNode node3 = new PriorityNode("node3");

        pll.insert(node1);
        pll.insert(node2);
        pll.insert(node3);
        pll.touch(node2);
        assertEquals(null, node2.getLeftNode());
        assertEquals(node3, node2.getRightNode());
        assertEquals(node2, node3.getLeftNode());
        assertEquals(node1, node3.getRightNode());
        assertEquals(node3, node1.getLeftNode());
        assertEquals(null, node1.getRightNode());
    }

    @Test
    public void tailTouchLinkedList() {
        PriorityLinkedList pll = new PriorityLinkedList(4);
        PriorityNode node1 = new PriorityNode("node1");
        PriorityNode node2 = new PriorityNode("node2");
        PriorityNode node3 = new PriorityNode("node3");

        pll.insert(node1);
        pll.insert(node2);
        pll.insert(node3);
        pll.touch(node1);
        assertEquals(null, node1.getLeftNode());
        assertEquals(node3, node1.getRightNode());
        assertEquals(node1, node3.getLeftNode());
        assertEquals(node2, node3.getRightNode());
        assertEquals(node3, node2.getLeftNode());
        assertEquals(null, node2.getRightNode());
    }

    @Test
    public void removeLinkedList() {
        PriorityLinkedList pll = new PriorityLinkedList(3);
        PriorityNode node1 = new PriorityNode("node1");
        PriorityNode node2 = new PriorityNode("node2");
        PriorityNode node3 = new PriorityNode("node3");

        pll.insert(node1);
        pll.insert(node2);
        pll.insert(node3);
        pll.removeOldest();
        assertEquals(null, node3.getLeftNode());
        assertEquals(node2, node3.getRightNode());
        assertEquals(node3, node2.getLeftNode());
        assertEquals(null, node2.getRightNode());
    }
}
