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
       return this.age < that.age ? 1 : -1;
    }
}

public class CustomComparable {
    public static void main(String[] args) {

        List<Person> people = new ArrayList<>();
        people.add(new Person("Ben", 20));
        people.add(new Person("Anna", 21));
        people.add(new Person("Messi", 38));
        people.add(new Person("Pedri", 23));

        Collections.sort(people);

        for(Person p : people) {
            System.out.println(p.name + " - " + p.age);
        }
    }
}
