package Containers;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;  // array of items
    private int size;      // number of elements

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];  // initial capacity of 2
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // resize the underlying array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot enqueue null item");
        }

        // double size of array if necessary
        if (size == items.length) {
            resize(2 * items.length);
        }

        // add item
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }

        // select a random item
        int randomIndex = StdRandom.uniformInt(size);
        Item item = items[randomIndex];

        // move the last item to the random position (avoid holes)
        items[randomIndex] = items[size - 1];
        items[size - 1] = null;  // avoid loitering
        size--;

        // shrink the array if necessary
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }

        int randomIndex = StdRandom.uniformInt(size);
        return items[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffledItems;
        private int currentIndex;

        public RandomizedQueueIterator() {
            // Create a copy of the items array
            shuffledItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                shuffledItems[i] = items[i];
            }

            // Shuffle the copied array
            StdRandom.shuffle(shuffledItems, 0, size);

            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            return shuffledItems[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        System.out.println("=== RandomizedQueue Unit Testing ===");

        // Test isEmpty() and size() on empty queue
        System.out.println("\n1. Testing constructor, isEmpty(), and size():");
        System.out.println("  Queue created");
        System.out.println("  isEmpty(): " + queue.isEmpty());
        System.out.println("  size(): " + queue.size());

        // Test enqueue()
        System.out.println("\n2. Testing enqueue():");
        queue.enqueue("A");
        System.out.println("  Enqueued 'A'");
        System.out.println("  isEmpty(): " + queue.isEmpty());
        System.out.println("  size(): " + queue.size());

        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");
        System.out.println("  Enqueued 'B', 'C', 'D', 'E'");
        System.out.println("  size(): " + queue.size());

        // Test sample()
        System.out.println("\n3. Testing sample() (should be random):");
        for (int i = 0; i < 5; i++) {
            System.out.println("  Sample " + (i+1) + ": " + queue.sample());
        }
        System.out.println("  size after sampling: " + queue.size());

        // Test iterator
        System.out.println("\n4. Testing iterator (should be in random order):");
        System.out.println("  First iterator:");
        for (String s : queue) {
            System.out.println("    " + s);
        }

        System.out.println("  Second iterator (likely different order):");
        for (String s : queue) {
            System.out.println("    " + s);
        }

        // Test dequeue()
        System.out.println("\n5. Testing dequeue() (should be random):");
        System.out.println("  Dequeued: " + queue.dequeue());
        System.out.println("  size(): " + queue.size());
        System.out.println("  Dequeued: " + queue.dequeue());
        System.out.println("  size(): " + queue.size());
        System.out.println("  Dequeued: " + queue.dequeue());
        System.out.println("  size(): " + queue.size());

        // Test edge cases and exceptions
        System.out.println("\n6. Testing exception handling:");

        // Test remaining elements
        System.out.println("\n7. Removing remaining elements:");
        while (!queue.isEmpty()) {
            System.out.println("  Dequeued: " + queue.dequeue());
        }
        System.out.println("  Queue is now empty. size(): " + queue.size());

        try {
            queue.dequeue();
            System.out.println("  ERROR: dequeue() should have thrown an exception");
        } catch (NoSuchElementException e) {
            System.out.println("  Successfully caught exception for dequeue() on empty queue");
        }

        try {
            queue.sample();
            System.out.println("  ERROR: sample() should have thrown an exception");
        } catch (NoSuchElementException e) {
            System.out.println("  Successfully caught exception for sample() on empty queue");
        }

        try {
            queue.enqueue(null);
            System.out.println("  ERROR: enqueue(null) should have thrown an exception");
        } catch (IllegalArgumentException e) {
            System.out.println("  Successfully caught exception for enqueue(null)");
        }

        // Test iterator on empty queue
        System.out.println("\n8. Testing iterator on empty queue:");
        Iterator<String> it = queue.iterator();
        System.out.println("  hasNext(): " + it.hasNext());

        try {
            it.next();
            System.out.println("  ERROR: next() should have thrown an exception");
        } catch (NoSuchElementException e) {
            System.out.println("  Successfully caught exception for next() on empty iterator");
        }

        try {
            it.remove();
            System.out.println("  ERROR: remove() should have thrown an exception");
        } catch (UnsupportedOperationException e) {
            System.out.println("  Successfully caught exception for remove() on iterator");
        }

        // Test resizing by adding many items
        System.out.println("\n9. Testing resizing by adding 20 items:");
        for (int i = 0; i < 20; i++) {
            queue.enqueue("Item " + i);
        }
        System.out.println("  Added 20 items");
        System.out.println("  size(): " + queue.size());

        // Test iteration over larger queue
        System.out.println("\n10. Iterating over larger queue (showing first 5 items):");
        int count = 0;
        for (String s : queue) {
            if (count < 5) {
                System.out.println("    " + s);
                count++;
            } else {
                break;
            }
        }
        System.out.println("  ... (remaining items)");

        System.out.println("\n=== End of Unit Testing ===");
    }
}