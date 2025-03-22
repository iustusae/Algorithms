package Containers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class SStack<Item>  {
    private class Node {
        Item item;
        Node next;

        Node(Item item) {
            this.item = item;
            next = null;
        }
    }

    Node head;

    public SStack() {
        head = null;
    }

    public SStack(Item item) {
        head = new Node(item);
    }

    public void push(Item item) {
        Node newNode = new Node(item);
        if (head != null) {
            newNode.next = head;
        }
        head = newNode;
    }

    public Item pop() {
        if (head == null) {
            return null;
        }
        Item item = head.item;
        head = head.next;
        return item;
    }



}
