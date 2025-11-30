import java.util.*;

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class CustomComparator {
    public static void main(String[] args) {
        
        // Comparator to sort Person objects based on age
        Comparator<Person> ageComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.age > p2.age ? 1 : -1;
            }
        };

        List<Person> people = new ArrayList<>();
        people.add(new Person("Ben", 20));
        people.add(new Person("Anna", 21));
        people.add(new Person("Messi", 38));
        people.add(new Person("Pedri", 23));
        
        Collections.sort(people, ageComparator);
        for(Person p : people) {
            System.out.println(p.name + " - " + p.age);
        }
    }
}
