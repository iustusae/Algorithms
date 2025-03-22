package Containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Bag<Item extends Scanner> implements Iterable<Item> {
    List<Item> items;

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

}
