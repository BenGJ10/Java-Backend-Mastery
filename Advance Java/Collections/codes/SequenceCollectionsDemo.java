import java.util.*;

public class SequenceCollectionsDemo {
    public static void main(String[] args) {

        System.out.println("===== SequencedCollection Demo (ArrayList) =====");
        SequencedCollection<String> sc = new ArrayList<>();

        sc.addFirst("A");
        sc.addLast("B");
        sc.addLast("C");

        System.out.println("Original: " + sc);
        System.out.println("First: " + sc.getFirst());
        System.out.println("Last: " + sc.getLast());

        SequencedCollection<String> scRev = sc.reversed();
        System.out.println("Reversed (view): " + scRev);

        sc.addLast("D");
        System.out.println("After adding D to original: " + sc);
        System.out.println("Reversed reflects change: " + scRev);

        System.out.println("\n===== SequencedSet Demo (LinkedHashSet) =====");
        SequencedSet<Integer> ss = new LinkedHashSet<>();

        ss.addFirst(10);
        ss.addLast(20);
        ss.addLast(30);

        System.out.println("Set: " + ss);
        System.out.println("First: " + ss.getFirst());
        System.out.println("Last: " + ss.getLast());

        SequencedSet<Integer> ssRev = ss.reversed();
        System.out.println("Reversed Set: " + ssRev);

        ss.removeLast();
        System.out.println("After removing last: " + ss);
        System.out.println("Reversed Updated: " + ssRev);

        System.out.println("\n===== SequencedMap Demo (LinkedHashMap) =====");
        SequencedMap<Integer, String> map = new LinkedHashMap<>();

        map.putFirst(1, "One");
        map.putLast(2, "Two");
        map.putLast(3, "Three");

        System.out.println("Map: " + map);
        System.out.println("First Entry: " + map.firstEntry());
        System.out.println("Last Entry: " + map.lastEntry());

        SequencedMap<Integer, String> revMap = map.reversed();
        System.out.println("Reversed Map View: " + revMap);

        map.putLast(4, "Four");
        System.out.println("After adding 4: " + map);
        System.out.println("Reversed Updated: " + revMap);
    }
}