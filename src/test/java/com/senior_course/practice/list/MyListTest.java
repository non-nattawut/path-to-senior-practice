package com.senior_course.practice.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyListTest {
    private MyList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Nested
    @DisplayName("Initialization Tests")
    class InitializationTests {

        @Test
        @DisplayName("Should create empty list with default constructor")
        void testDefaultConstructor() {
            assertNotNull(list);
            // Since there is no isEmpty() or size() method, we rely on implicit checks
            // or we could check that accessing index 0 throws an exception (if implemented strictly)
            // or returns null depending on impl.
        }

        @Test
        @DisplayName("Should create list from Array")
        void testArrayConstructor() {
            String[] data = {"A", "B", "C"};
            MyList<String> arrayList = new MyList<>(data);

            assertEquals("A", arrayList.get(0));
            assertEquals("B", arrayList.get(1));
            assertEquals("C", arrayList.get(2));
        }

        @Test
        @DisplayName("Should create list from Static Factory (Varargs)")
        void testStaticFactoryVarargs() {
            MyList<Integer> intList = MyList.of(1, 2, 3);
            assertEquals(1, intList.get(0));
            assertEquals(3, intList.get(2));
        }

        @Test
        @DisplayName("Should create list from Static Factory (Collection)")
        void testStaticFactoryCollection() {
            List<String> javaList = Arrays.asList("X", "Y", "Z");
            MyList<String> myList = MyList.of(javaList);

            assertEquals("X", myList.get(0));
            assertEquals("Z", myList.get(2));
        }
    }

    @Nested
    @DisplayName("Core Operations: Add & Get")
    class CoreOperationsTests {

        @Test
        @DisplayName("Should add elements and retrieve them")
        void testAddAndGet() {
            list.add("Hello");
            list.add("World");

            assertEquals("Hello", list.get(0));
            assertEquals("World", list.get(1));
        }

        @Test
        @DisplayName("Should resize array when capacity is exceeded")
        void testResizeLogic() {
            // INITIAL_SIZE is 16. Let's add 20 items to trigger resize.
            int itemsToAdd = 20;
            for (int i = 0; i < itemsToAdd; i++) {
                list.add("Item " + i);
            }

            assertEquals("Item 0", list.get(0));
            assertEquals("Item 19", list.get(19));
            // Verify no IndexOutOfBoundsException occurred
        }
    }

    @Nested
    @DisplayName("Remove Operations")
    class RemoveTests {

        @Test
        @DisplayName("Should remove element from the middle and shift remaining")
        void testRemoveMiddle() {
            list.add("A");
            list.add("B");
            list.add("C");

            // Remove 'B' at index 1
            list.remove(1);

            assertEquals("A", list.get(0));

            // Expected: 'C' shifts to index 1
            assertEquals("C", list.get(1), "Index 1 should contain 'C' after removing 'B'");
        }

        @Test
        @DisplayName("Should remove element from the beginning")
        void testRemoveFirst() {
            list.add("A");
            list.add("B");

            list.remove(0);

            assertEquals("B", list.get(0), "Index 0 should be 'B' after removing 'A'");
        }
    }

    @Nested
    @DisplayName("Functional Operations: Map & Filter")
    class FunctionalTests {

        @Test
        @DisplayName("Map: Should convert Integer to String")
        void testMap() {
            MyList<Integer> intList = MyList.of(1, 2, 3);

            MyList<String> strList = intList.map(i -> "Num-" + i);

            assertEquals("Num-1", strList.get(0));
            assertEquals("Num-2", strList.get(1));
            assertEquals("Num-3", strList.get(2));
        }

        @Test
        @DisplayName("Filter: Should only keep elements matching predicate")
        void testFilter() {
            MyList<Integer> intList = MyList.of(1, 2, 3, 4, 5);

            // Keep only even numbers
            MyList<Integer> evenList = intList.filter(i -> i % 2 == 0);

            // Based on logic, new list should have 2 and 4 at indices 0 and 1
            assertEquals(2, evenList.get(0));
            assertEquals(4, evenList.get(1));

            // Checking next index might be tricky without size(),
            // but in a clean impl, accessing index 2 should ideally be empty/null or throw exception.
        }

        @Test
        @DisplayName("Map: Should handle empty list gracefully")
        void testMapEmpty() {
            MyList<String> empty = new MyList<>();
            MyList<Integer> result = empty.map(String::length);
            // Should verify result is usable or empty, mostly ensuring no crash
            assertNotNull(result);
        }
    }

    @Nested
    @DisplayName("Edge Cases & Exceptions")
    class EdgeCaseTests {

        @Test
        @DisplayName("Get: Should throw exception for negative index")
        void testGetNegativeIndex() {
            list.add("Test");
            // Expecting standard array exception behavior since custom exceptions aren't defined
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(-1));
        }

        @Test
        @DisplayName("Get: Should throw exception for Out of Bounds index")
        void testGetOutOfBounds() {
            // Internal array is size 16, so accessing 100 should throw
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(100));
        }

        @Test
        @DisplayName("Logic: Map should skip nulls (based on implementation)")
        void testMapWithNulls() {
            // Your implementation explicitly checks (d != null)
            // If we somehow add a null, it should be skipped in the new map result
            list.add("A");
            list.add(null);
            list.add("B");

            MyList<String> result = list.map(s -> s + "!");

            // Original: [A, null, B]
            // Map Result (skips nulls): [A!, B!]
            assertEquals("A!", result.get(0));
            assertEquals("B!", result.get(1));
        }
    }
}
