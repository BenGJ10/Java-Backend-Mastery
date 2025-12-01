// Implementation of Comparable and Comparator in Java
import java.util.*;

class Person implements Comparable<Person> {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person that) {
       return this.age < that.age ? 1 : -1; // Sort by age in descending order
    }
}

public class ComparableAndComparator {
    public static void main(String[] args) {
        
        Comparator<Person> nameComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.name.compareTo(p2.name); // Sort by name in ascending order
            }
        };

        List<Person> people = new ArrayList<>();
        people.add(new Person("Ben", 20));
        people.add(new Person("Anna", 21));
        people.add(new Person("Messi", 38));
        people.add(new Person("Pedri", 23));

        Collections.sort(people);

        System.out.println("Sorted by age (using Comparable):");
        for(Person p : people) {
            System.out.println(p.name + " - " + p.age);
        }

        System.out.println("\nSorted by name (using Comparator):");
        Collections.sort(people, nameComparator);
        for(Person p : people) {
            System.out.println(p.name + " - " + p.age);
        }
    }   
}
