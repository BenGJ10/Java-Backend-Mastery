import java.util.*;

public class ComparatorDemo {
    public static void main(String args[]){
        
        // Comparator to sort integers based on the last digit
        Comparator<Integer> intComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return (a % 10) > (b % 10) ? 1 : -1;
            }
        };

        // Comparator to sort strings according to their lengths
        Comparator<String> stringComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        };
         
        List<Integer> list = new ArrayList<>();
        list.add(23);
        list.add(5);
        list.add(87);
        list.add(12);

        System.out.println("Before Sorting: " + list);
        Collections.sort(list, intComparator);
        System.out.println("After Sorting (based on last digit): " + list);

        List<String> strList = new ArrayList<>();
        strList.add("Apple");
        strList.add("Banana");
        strList.add("Kiwi");
        strList.add("Mango");

        System.out.println("Before Sorting: " + strList);
        Collections.sort(strList, stringComparator);
        System.out.println("After Sorting (based on length): " + strList);

        
    }
}
