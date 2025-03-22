package Containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private Node head, tail;
    private int size;

    // construct an empty deque
    public Deque() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node first = new Node();
        first.item = item;
        first.next = head.next;
        first.prev = head;
        head.next.prev = first;
        head.next = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node last = new Node();
        last.item = item;
        last.next = tail;
        last.prev = tail.prev;
        tail.prev.next = last;
        tail.prev = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Node first = head.next;
        head.next = first.next;
        first.next.prev = head;
        size--;
        return first.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Node last = tail.prev;
        tail.prev = last.prev;
        last.prev.next = tail;
        size--;
        return last.item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head.next;

        @Override
        public boolean hasNext() {
            return current != tail;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Unit testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println("isEmpty(): " + deque.isEmpty());
        deque.addFirst("A");
        deque.addLast("B");
        deque.addFirst("C");
        deque.addLast("D");
        System.out.println("size(): " + deque.size());
        for (String item : deque) System.out.println(item);
        System.out.println("removeFirst(): " + deque.removeFirst());
        System.out.println("removeLast(): " + deque.removeLast());
        System.out.println("size(): " + deque.size());
        for (String item : deque) System.out.println(item);
    }
}
