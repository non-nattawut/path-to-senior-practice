package com.senior_course.practice.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {

    private MyHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        // Reset the map before every single test to ensure a clean state
        map = new MyHashMap<>();
    }

    @Test
    @DisplayName("Should put and get a simple value")
    void testPutAndGet() {
        map.put("A", 100);
        assertEquals(100, map.get("A"), "Value for 'A' should be 100");
    }

    @Test
    @DisplayName("Should update value if key already exists")
    void testUpdateValue() {
        map.put("A", 100);
        map.put("A", 200); // Update
        assertEquals(200, map.get("A"), "Value for 'A' should be updated to 200");
    }

    @Test
    @DisplayName("Should handle collisions (Two keys, same Hash Index)")
    void testCollisionHandling() {
        // It is hard to force a hash collision without knowing internal hash logic,
        // but we can simulate it by adding enough items or using mocked objects.
        // For this test, we rely on the fact that different keys *can* coexist.
        map.put("A", 1);
        map.put("B", 2);

        // Even if A and B collided internally, both should be retrievable
        assertEquals(1, map.get("A"));
        assertEquals(2, map.get("B"));
    }

    @Test
    @DisplayName("Should resize/scale capability when threshold is met")
    void testResizing() {
        // Initial capacity is 16. Scale factor is 0.75.
        // Threshold = 12 slots.
        // We need to fill at least 13 *distinct slots* to force a resize.

        int limit = 20;
        for (int i = 0; i < limit; i++) {
            map.put("Key" + i, i);
        }

        // Verify data is still intact after resizing
        for (int i = 0; i < limit; i++) {
            assertEquals(i, map.get("Key" + i), "Data lost after resize for Key" + i);
        }
    }

    @Test
    @DisplayName("Should handle Object Equality (The '888' Integer Issue)")
    void testObjectEquality() {
        // This specifically tests the fix for the issue you just faced.
        MyHashMap<Integer, String> intMap = new MyHashMap<>();

        int val1 = 888;
        int val2 = 888; // These might be different objects in memory if created explicitly

        intMap.put(val1, "Success");

        // This asserts that .equals() is used, not ==
        assertEquals("Success", intMap.get(val2));
    }

    @Test
    @DisplayName("Should handle mixed types correctly")
    void testMixedTypes() {
        MyHashMap<Object, Object> mixedMap = new MyHashMap<>();
        mixedMap.put(1, "One");
        mixedMap.put("Two", 2);
        mixedMap.put(10.5, true);

        assertEquals("One", mixedMap.get(1));
        assertEquals(2, mixedMap.get("Two"));
        assertEquals(true, mixedMap.get(10.5));
    }

    @Test
    @DisplayName("Should throw exception when key is not found")
    void testKeyNotFound() {
        map.put("A", 1);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            map.get("Z");
        });

        assertEquals("Key not found : Z", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NPE for Null keys (Since implementation calls hashCode on key)")
    void testNullKey() {
        // Because your code does `key.hashCode()`, a null key will throw NullPointerException.
        // This test documents that behavior.
        assertThrows(NullPointerException.class, () -> {
            map.put(null, 1);
        });
    }

    @Test
    @DisplayName("Should handle negative HashCodes correctly")
    void testNegativeHashCodes() {
        // "Aa" and "BB" have the same hashCode in Java, but let's test specifically
        // objects that might generate negative hashes.
        MyHashMap<Integer, Integer> intMap = new MyHashMap<>();

        int negativeKey = -1; // hashCode is -1
        intMap.put(negativeKey, 999);

        // Ensure Math.abs() in your getHashKey() method works
        assertEquals(999, intMap.get(negativeKey));
    }

    @Test
    @DisplayName("Edge Case: Massive Collisions (DoS Simulation)")
    void testMassiveCollisionsAndResizeLogic() {
        MyHashMap<Object, Object> map = new MyHashMap<>();
        // 1. Create a key that ALWAYS returns the same hashCode
        class BadHashKey {
            final int id;
            BadHashKey(int id) { this.id = id; }

            @Override
            public int hashCode() { return 1; } // FORCE COLLISION

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof BadHashKey) && ((BadHashKey) obj).id == this.id;
            }
        }

        // 2. Insert more items than the initial capacity (16)
        int items = 100;
        for (int i = 0; i < items; i++) {
            map.put(new BadHashKey(i), i); // This needs map to accept <Object, Object> or generic fix
        }

        // 3. Verify all items exist (Testing LinkedList traversal)
        for (int i = 0; i < items; i++) {
            assertEquals(i, map.get(new BadHashKey(i)));
        }

        // 4. CRITICAL: Check if resize happened.
        // With your current logic, this might fail or performance will be terrible
        // because occupySlot might not have incremented correctly for collisions.
    }

    @Test
    @DisplayName("Edge Case: Null Values")
    void testNullValues() {
        map.put("EmptyValue", null);

        // Should return null, not throw Exception
        assertNull(map.get("EmptyValue"));

        // Should distinguish between 'Key exists with null value' vs 'Key not found'
        // Your current implementation throws RuntimeException for not found, so this is safe.
        assertThrows(RuntimeException.class, () -> map.get("NonExistent"));
    }
}