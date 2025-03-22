package Containers;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        if(args.length != 1) {
            throw new IllegalArgumentException("Usage: Permutation <input file>");
        }
        int n = Integer.parseInt(args[0]);
        var queue = new RandomizedQueue<String>();
        while(!StdIn.isEmpty() && (queue.size() < n)) {
            queue.enqueue(StdIn.readString());
        }

        while(!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }



    }
}
